package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.GalleryImages;
import com.fatesg.fashion_boot.entity.Options;
import com.fatesg.fashion_boot.entity.Product;
import com.fatesg.fashion_boot.repository.ProductRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository repository;

    public Product save(Product product) {
        for (Options opt : product.getOptions()) {
            opt.setProduct(product);
        }
        for (GalleryImages images : product.getGallery()) {
            images.setProduct(product);
        }
        return repository.save(product);
    }

    public Page<Product> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
        // return repository.findByName(name, pageable);
    }

    public Page<Product> listAllPageName(String name, Pageable pageable) {
//        return repository.findAll(pageable);
        return repository.findByName(name, pageable);
    }

    public List<Product> listAll() {
        return repository.findAll();

    }


    public Product findByIdOrThrowRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Marca não localizada"));

    }


    public void delete(Long id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new com.fatesg.fashion_boot.service.exception.DataIntegrityViolationException("Não é posssivel apagar, pois está associada a uma ordem de compra");
        }
    }

    public void replace(Product objeto) {
        for (Options opt : objeto.getOptions()) {
            opt.setProduct(objeto);
        }
        for (GalleryImages images : objeto.getGallery()) {
            images.setProduct(objeto);
        }
        try {
            findByIdOrThrowRequestException(objeto.getId());
            repository.save(objeto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Product> findProductsByCategoryName(String name) {
        return repository.findProductByCategoryName(name);
    }

    public List<Product> findProductByBrandName(String name) {
        return repository.findProductByBrandName(name);
    }

}

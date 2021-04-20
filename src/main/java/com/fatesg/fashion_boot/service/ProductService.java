package com.fatesg.fashion_boot.service;

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
        product.setId(null);
        return repository.save(product);
    }

    public Page<Product> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
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
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a uma pessoa");
        }
    }

    public void replace(Product objeto) {
        Product categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

    public List<Product> findProductsByCategoryName(String name){
        return repository.findProductByCategoryName(name);
    }

}

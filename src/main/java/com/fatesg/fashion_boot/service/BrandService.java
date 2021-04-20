package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Brand;
import com.fatesg.fashion_boot.repository.BrandRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    final BrandRepository repository;

    public Brand save(Brand category) {
        category.setId(null);
        return repository.save(category);
    }

    public Page<Brand> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Brand> listAll() {
        return repository.findAll();

    }

    public Brand findByName(String name) {
        return repository.findByName(name);

    }

    public Brand findByIdOrThrowRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Marca não localizada"));

    }


    public void delete(Long id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a um produto");
        }
    }

    public void replace(Brand objeto) {
        Brand categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(objeto);

    }

}

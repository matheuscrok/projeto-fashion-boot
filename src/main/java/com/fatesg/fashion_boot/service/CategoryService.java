package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Category;
import com.fatesg.fashion_boot.service.exception.*;
import com.fatesg.fashion_boot.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoriaRepository repository;

    public Category save(Category category) {
        category.setId(null);
        return repository.save(category);
    }

    public Page<Category> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Category> listAll() {
        return repository.findAll();

    }

    public Category findByName(String name) {
        return repository.findByName(name);

    }

    public Category findByIdOrThrowRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadResquestException("Categoria não localizada"));
    }


    public void delete(Long id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a um produto");
        }
    }

    public void replace(Category objeto) {
        Category categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

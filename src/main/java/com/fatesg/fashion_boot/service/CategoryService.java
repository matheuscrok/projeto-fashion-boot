package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Category;
import com.fatesg.fashion_boot.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    final CategoriaRepository repository;

    public Category save(Category category){
        return repository.save(category);
    }


}

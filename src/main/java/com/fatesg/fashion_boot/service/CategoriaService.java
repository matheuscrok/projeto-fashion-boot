package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Categoria;
import com.fatesg.fashion_boot.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    final CategoriaRepository repository;

    public Categoria save(Categoria categoria){
        return repository.save(categoria);
    }


}

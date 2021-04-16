package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Categoria;
import com.fatesg.fashion_boot.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    final CategoriaService service;

    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria objeto){
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);

    }

}

package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Category;
import com.fatesg.fashion_boot.service.CategoryService;
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
public class CategoryController {

    final CategoryService service;

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category objeto){
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);

    }

}

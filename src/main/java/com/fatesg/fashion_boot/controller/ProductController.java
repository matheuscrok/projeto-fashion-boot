package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Product;
import com.fatesg.fashion_boot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductService service;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product objeto){
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Product>> listPage(Pageable pageable){
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }
    @GetMapping
    public ResponseEntity<List<Product>> list(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Product obj){
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

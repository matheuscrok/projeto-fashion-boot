package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.City;
import com.fatesg.fashion_boot.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citys")
@RequiredArgsConstructor
public class CityController {

    final CityService service;

    @PostMapping
    public ResponseEntity<City> save(@RequestBody City objeto){
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<City>> listPage(Pageable pageable){
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }
    @GetMapping
    public ResponseEntity<List<City>> list(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody City obj){
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

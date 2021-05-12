package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Brand;
import com.fatesg.fashion_boot.entity.Product;
import com.fatesg.fashion_boot.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    final BrandService service;

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<Brand> save(@RequestBody Brand objeto) {
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/page")
    public ResponseEntity<Page<Brand>> listPage(@Param(value = "name") String name, Pageable pageable) {
        if (name.equals("")) {
            return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
        }

        return ResponseEntity.ok(service.listAllPageName(name, pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @GetMapping
    public ResponseEntity<List<Brand>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @GetMapping("/find")  //   /find?name={oque voce digitou para procurar}  - name é a variavel aqui em baixo
    public ResponseEntity<Brand> findByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Brand obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

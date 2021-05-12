package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Options;
import com.fatesg.fashion_boot.service.OptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/options")
@RequiredArgsConstructor
public class OptionsController {

    final OptionsService service;

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<Options> save(@RequestBody Options objeto) {
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Options>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @GetMapping
    public ResponseEntity<List<Options>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Options> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping(path = "/product/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAllByProductId(@PathVariable Long id) {
        service.deleteAllByProductId(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Options obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

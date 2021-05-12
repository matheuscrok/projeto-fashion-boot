package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.State;
import com.fatesg.fashion_boot.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

    final StateService service;

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<State> save(@RequestBody State objeto) {
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/page")
    public ResponseEntity<Page<State>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @GetMapping
    public ResponseEntity<List<State>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody State obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

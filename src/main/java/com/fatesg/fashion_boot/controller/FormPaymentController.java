package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.FormPayment;
import com.fatesg.fashion_boot.service.FormPaymentService;
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
@RequestMapping("/form_payments")
@RequiredArgsConstructor
public class FormPaymentController {

    final FormPaymentService service;

    @RolesAllowed({"ADMIN", "USER"})
    @PostMapping
    public ResponseEntity<FormPayment> save(@RequestBody FormPayment objeto) {
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/page")
    public ResponseEntity<Page<FormPayment>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<FormPayment>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<FormPayment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @RolesAllowed({"ADMIN", "USER"})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody FormPayment obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

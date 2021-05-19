package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Address;
import com.fatesg.fashion_boot.service.AddressService;
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
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    final AddressService service;

    @RolesAllowed({"USER", "ADMIN"})
    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {
        return new ResponseEntity<>(service.save(address), HttpStatus.CREATED);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/page")
    public ResponseEntity<Page<Address>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping
    public ResponseEntity<List<Address>> list() {
        return ResponseEntity.ok(service.listAll());
    }

   // @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @RolesAllowed({"USER", "ADMIN"})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Address obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

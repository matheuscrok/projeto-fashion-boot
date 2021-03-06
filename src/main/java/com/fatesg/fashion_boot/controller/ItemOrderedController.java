package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.ItemOrdered;
import com.fatesg.fashion_boot.service.ItemOrderedService;
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
@RequestMapping("/item_ordered")
@RequiredArgsConstructor
public class ItemOrderedController {

    final ItemOrderedService service;

    @RolesAllowed({"ADMIN", "USER"})
    @PostMapping
    public ResponseEntity<ItemOrdered> save(@RequestBody ItemOrdered objeto) {
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/page")
    public ResponseEntity<Page<ItemOrdered>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

   @RolesAllowed({"ADMIN", "USER"})
    @GetMapping
    public ResponseEntity<List<ItemOrdered>> list() {
        return ResponseEntity.ok(service.listAll());
    }

   // @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/orders/{id}")
    public ResponseEntity<List<ItemOrdered>> listAllByOrdemId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listAllByOrdemId(id));
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{id}")
    public ResponseEntity<ItemOrdered> findById(@PathVariable Long id) {
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
    public ResponseEntity<Void> replace(@RequestBody ItemOrdered obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

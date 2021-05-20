package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Usuario;
import com.fatesg.fashion_boot.service.UserService;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    final UserService service;

 //   @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        try {
            service.findByIdOrThrowRequestException(usuario.getSub());
        }catch (Exception e){
            return new ResponseEntity<>(service.save(usuario), HttpStatus.CREATED);
        }
        return null;

    }


    @RolesAllowed("ADMIN")
    @GetMapping("/page")
    public ResponseEntity<Page<Usuario>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    //@RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<Usuario>> list() {
        return ResponseEntity.ok(service.listAll());
    }

  //  @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

   // @RolesAllowed({"ADMIN", "USER"})
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Usuario usuario) {
        service.replace(usuario);
        return ResponseEntity.noContent().build();
    }


}

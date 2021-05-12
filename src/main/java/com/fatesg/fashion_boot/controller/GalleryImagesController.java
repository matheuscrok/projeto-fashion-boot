package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.GalleryImages;
import com.fatesg.fashion_boot.service.GalleryImagesService;
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
@RequestMapping("/galleryImages")
@RequiredArgsConstructor
public class GalleryImagesController {

    final GalleryImagesService service;

    @RolesAllowed("ADMIN")
    @PostMapping
    public ResponseEntity<GalleryImages> save(@RequestBody GalleryImages galleryImages) {
        return new ResponseEntity<>(service.save(galleryImages), HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<GalleryImages>> listPage(Pageable pageable) {
        return ResponseEntity.ok(service.listAllPage(pageable));//animes?size=5&page=2 - 2 pode mudar
    }

    @GetMapping
    public ResponseEntity<List<GalleryImages>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<List<GalleryImages>> listAllByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(service.listAllByProductId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryImages> findById(@PathVariable Long id) {
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
    public ResponseEntity<Void> replace(@RequestBody GalleryImages obj) {
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

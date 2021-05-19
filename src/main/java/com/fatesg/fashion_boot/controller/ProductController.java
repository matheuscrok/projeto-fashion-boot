package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.GalleryImages;
import com.fatesg.fashion_boot.entity.Product;
import com.fatesg.fashion_boot.service.GalleryImagesService;
import com.fatesg.fashion_boot.service.MinioAdapter;
import com.fatesg.fashion_boot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductService service;
    final GalleryImagesService galleryimagesService;


    final MinioAdapter minioAdapter;

    @Value("${minio.url}")
    private String url;

    /**
     * Login account
     */
    @Value("${minio.buckek.name}")
    private String bucket;

    /**
     * Login password
     */

    @PostMapping
    public ResponseEntity<Product> save(@Valid @RequestBody Product objeto) {
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }

    //    @GetMapping("/page")
//    public ResponseEntity<Page<Product>> listPage(Pageable pageable){
//        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
//    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Product>> listPage(@Param(value = "name") String name, Pageable pageable) {
        if (name == "") {
            return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
        }

        return ResponseEntity.ok(service.listAllPageName(name, pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(service.listAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
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
    public ResponseEntity<Product> replace(@RequestBody Product obj) {
        service.replace(obj);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/categoria/{name}")
    public ResponseEntity<List<Product>> listProductsByCatId(@PathVariable String name) {
        return ResponseEntity.ok(service.findProductsByCategoryName(name));
    }

    @RolesAllowed("ADMIN")
    @PostMapping(path = "/salvarfoto", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<GalleryImages> salvarFoto(@RequestPart(value = "file", required = false) MultipartFile files,
                                                    @RequestParam(value = "product_id") Long product_id) throws IOException {
        minioAdapter.uploadFile(files.getOriginalFilename(), files.getBytes());

        Map<String, String> result = new HashMap<>();
        result.put("key", files.getOriginalFilename());
        result.put("url", this.url);
        result.put("bucket", this.bucket);
        String url = this.url + "/" + this.bucket + "/%20" + files.getOriginalFilename();
        GalleryImages galleryImages = new GalleryImages();
        galleryImages.setProduct(service.findByIdOrThrowRequestException(product_id));
        galleryImages.setName(url);
        galleryImages = galleryimagesService.save(galleryImages);
        return ResponseEntity.ok().body(galleryImages);
    }


}

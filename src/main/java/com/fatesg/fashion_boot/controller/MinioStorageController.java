package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.GalleryImages;
import com.fatesg.fashion_boot.entity.Product;
import com.fatesg.fashion_boot.service.MinioAdapter;
import com.fatesg.fashion_boot.service.ProductService;
import com.google.api.client.util.Lists;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/files")
public class MinioStorageController {
    @Autowired
    MinioAdapter minioAdapter;
    @Autowired
    ProductService productService;

    @Value("${minio.url}")
    private String url;

    /**
     * Login account
     */
    @Value("${minio.buckek.name}")
    private String bucket;

    /**
     * login password
     */

    @GetMapping(path = "/buckets")
    public List<Bucket> listBuckets() {
        return minioAdapter.getAllBuckets();
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file")  MultipartFile files) throws IOException {
        minioAdapter.uploadFile(files.getOriginalFilename(), files.getBytes());
        Map<String, String> result = new HashMap<>();
        result.put("key", files.getOriginalFilename());
        result.put("url", this.url);
        result.put("bucket", this.bucket);
        String url = this.url + "" + this.bucket + "/%20" + files.getOriginalFilename();
        return url;
    }

    @RequestMapping(value = "/update",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(millis = 0L)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
    public void updateProductAndFile(@RequestPart(value = "file") MultipartFile[] file,
                                     @RequestPart(value = "produto") Product produto) throws IOException {
        List<GalleryImages> listaDeImagens = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            if(i == 0){
                minioAdapter.uploadFile(file[i].getOriginalFilename(), file[i].getBytes());
                String url = this.url + "" + this.bucket + "/%20" + file[i].getOriginalFilename();
                produto.setImg(url);


            }else{
                minioAdapter.uploadFile(file[i].getOriginalFilename(), file[i].getBytes());
                String url = this.url + "" + this.bucket + "/%20" + file[i].getOriginalFilename();
                listaDeImagens.add(new GalleryImages(null, url, null));

            }

        }
        produto.setGallery(listaDeImagens);
        productService.save(produto);



    }

    @GetMapping(path = "/download")
    public ResponseEntity<ByteArrayResource> uploadFile(@RequestParam(value = "file") String file) throws IOException {
        byte[] data = minioAdapter.getFile(file);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
                .body(resource);

    }
}
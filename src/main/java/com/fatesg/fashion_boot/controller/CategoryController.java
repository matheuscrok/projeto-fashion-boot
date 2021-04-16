package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.Category;
import com.fatesg.fashion_boot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoryController {

    final CategoryService service;

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category objeto){
        return new ResponseEntity<>(service.save(objeto), HttpStatus.CREATED);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Category>> listPage(Pageable pageable){
        return ResponseEntity.ok(service.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }
    @GetMapping
    public ResponseEntity<List<Category>> list(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdOrThrowRequestException(id));
    }

    @GetMapping("/find")  //   /find?name={oque voce digitou para procurar}  - name é a variavel aqui em baixo
    public ResponseEntity<Category> findByName(@RequestParam String name){
        return ResponseEntity.ok(service.findByName(name));
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Category obj){
        service.replace(obj);
        return ResponseEntity.noContent().build();
    }


}

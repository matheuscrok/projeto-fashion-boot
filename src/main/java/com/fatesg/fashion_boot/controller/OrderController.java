package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.*;
import com.fatesg.fashion_boot.service.*;
import com.fatesg.fashion_boot.util.DateUtil;
import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;
    final FormPaymentService formPaymentService;
    final AddressService addressService;
    final UserService userService;
    final ItemOrderedService itemOrderedService;
    final StateService stateService;
    final CityService cityService;


    @RolesAllowed("USER")
    @PostMapping
    public ResponseEntity<Ordem> save(@RequestBody Ordem objeto) {
        return new ResponseEntity<>(orderService.save(objeto), HttpStatus.CREATED);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/page")
    public ResponseEntity<Page<Ordem>> listPage(@Param(value = "name") String name, Pageable pageable) {
//        if(name.equals("")){
//            return ResponseEntity.ok(orderService.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
//        }

        return ResponseEntity.ok(orderService.listAllPageName(name, pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

//    @GetMapping("/page")
//    public ResponseEntity<Page<Ordem>> listPage(Pageable pageable) {
//        return ResponseEntity.ok(orderService.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
//    }

    //@RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<Ordem>> list() {
        return ResponseEntity.ok(orderService.listAll());
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{id}")
    public ResponseEntity<Ordem> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findByIdOrThrowRequestException(id));
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ADMIN")
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Ordem obj) {
        orderService.replace(obj);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @RequestMapping(value = "/compra",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(millis = 0L)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ResponseEntity<Ordem> compra(@RequestPart(value = "itemOrdered") ItemOrdered[] itemOrdered,
                                        @RequestPart(value = "ordem") Ordem ordem) {


        List<ItemOrdered> listaDeCarinho = new ArrayList<>();
        for (ItemOrdered item : itemOrdered) {
            item.setOrdem(ordem);
            ItemOrdered itemOrderedSave = this.itemOrderedService.save(item);
            listaDeCarinho.add(itemOrderedSave);
        }
        ordem.setItemOrdered(listaDeCarinho);
        ordem.setDate_purchase(new Date(new java.util.Date().getTime()));
        ordem.setStatus("Pendente");
        Ordem ordemSave = this.orderService.save(ordem);


        return ResponseEntity.ok().body(ordemSave);
    }


}

package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.entity.*;
import com.fatesg.fashion_boot.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    public ResponseEntity<Ordem> save(@RequestBody Ordem objeto) {
        return new ResponseEntity<>(orderService.save(objeto), HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Ordem>> listPage(Pageable pageable) {
        return ResponseEntity.ok(orderService.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
    }

    @GetMapping
    public ResponseEntity<List<Ordem>> list() {
        return ResponseEntity.ok(orderService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ordem> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findByIdOrThrowRequestException(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Ordem obj) {
        orderService.replace(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/compra",
            method = RequestMethod.POST,
            consumes = {"multipart/form-data"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed(millis = 0L)
    @Transactional(propagation= Propagation.REQUIRED, readOnly=false)
    public ResponseEntity<Ordem> compra(@RequestPart(value = "formPayment")FormPayment formPayment,
                                       @RequestPart(value = "address")Address address,
                                       @RequestPart(value = "usuario") Usuario usuario,
                                       @RequestPart(value = "itemOrdered") ItemOrdered[] itemOrdered,
                                       @RequestPart(value = "ordem")Ordem ordem){


        FormPayment paymentSave = this.formPaymentService.save(formPayment);
        Address adressSave = this.addressService.save(address);
        usuario.setAddress(adressSave);
        usuario.setForm_payment(paymentSave);
        Usuario usuarioSave = this.userService.save(usuario);
        List<ItemOrdered> listaDeCarinho = new ArrayList<>();
        for(ItemOrdered item : itemOrdered){
            ItemOrdered itemOrderedSave = this.itemOrderedService.save(item);
            listaDeCarinho.add(itemOrderedSave);
        }
        ordem.setItemOrdered(listaDeCarinho);
        Ordem ordemSave = this.orderService.save(ordem);

        return ResponseEntity.ok().body(ordemSave);
    }


}

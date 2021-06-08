package com.fatesg.fashion_boot.controller;

import com.fatesg.fashion_boot.config.mail.MailService;
import com.fatesg.fashion_boot.entity.ItemOrdered;
import com.fatesg.fashion_boot.entity.Ordem;
import com.fatesg.fashion_boot.entity.dto.OrderByDayDTO;
import com.fatesg.fashion_boot.entity.dto.OrderByStatusDTO;
import com.fatesg.fashion_boot.entity.dto.SaleByMonthDTO;
import com.fatesg.fashion_boot.service.ItemOrderedService;
import com.fatesg.fashion_boot.service.OrderService;
import com.fatesg.fashion_boot.util.DateUtil;
import com.fatesg.fashion_boot.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Timed;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;
    final ItemOrderedService itemOrderedService;
    final MailService mailService;


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

    @GetMapping("/sale-by-month")
    public ResponseEntity<List<SaleByMonthDTO>> findSaleByMonth() {
        return ResponseEntity.ok(orderService.findSaleByMonth());
    }

    @GetMapping("/order-by-status")
    public ResponseEntity<List<OrderByStatusDTO>> findOrderByStatus() {
        return ResponseEntity.ok(orderService.findOrderByStatus());
    }

    @GetMapping("/order-by-day")
    public ResponseEntity<List<OrderByDayDTO>> findOrderByDay() {
        return ResponseEntity.ok(orderService.findOrderByDay());
    }

//    @GetMapping("/page")
//    public ResponseEntity<Page<Ordem>> listPage(Pageable pageable) {
//        return ResponseEntity.ok(orderService.listAllPage(pageable)); //animes?size=5&page=2 - 2 pode mudar
//    }

    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<Ordem>> list() {
        return ResponseEntity.ok(orderService.listAll());
    }

    // @RolesAllowed("USER")
    @GetMapping("/usuario/{sub}")
    public ResponseEntity<List<Ordem>> findAllByUsuarioSub(@PathVariable String sub) {
        return ResponseEntity.ok(orderService.findAllByUsuarioSub(sub));
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
        try {

            List<ItemOrdered> listaDeCarinho = new ArrayList<>();
            for (ItemOrdered item : itemOrdered) {
                item.setOrdem(ordem);
                ItemOrdered itemOrderedSave = this.itemOrderedService.save(item);
                listaDeCarinho.add(itemOrderedSave);
            }
            ordem.setItemOrdered(listaDeCarinho);
            ordem.setDate_purchase((DateUtil.formatLocalDatetime(LocalDateTime.now())));
            ordem.setStatus("Pendente");
            Ordem ordemSave = this.orderService.save(ordem);
            this.mailService.sendEmail(ordemSave.getUsuario().getEmail(), "Compra Realizada", EmailUtil.compraFeita);
            return ResponseEntity.ok().body(ordemSave);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return null;
    }


}

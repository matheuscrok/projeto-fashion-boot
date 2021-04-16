package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Order;
import com.fatesg.fashion_boot.repository.OrderRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    final OrderRepository repository;

    public Order save(Order order) {
        order.setId(null);
        return repository.save(order);
    }

    public Page<Order> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Order> listAll() {
        return repository.findAll();

    }


    public Order findByIdOrThrowRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Marca não localizada"));

    }


    public void delete(Long id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a uma pessoa");
        }
    }

    public void replace(Order objeto) {
        Order categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

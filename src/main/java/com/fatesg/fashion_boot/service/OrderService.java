package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Ordem;
import com.fatesg.fashion_boot.entity.dto.OrderByDayDTO;
import com.fatesg.fashion_boot.entity.dto.OrderByStatusDTO;
import com.fatesg.fashion_boot.entity.dto.SaleByMonthDTO;
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

    public Ordem save(Ordem ordem) {
        ordem.setId(null);
        return repository.save(ordem);
    }

    public Page<Ordem> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Ordem> listAll() {
        return repository.findAll();

    }
    public List<Ordem> findAllByUsuarioSub(String sub) {
        return repository.findAllByUsuarioSub(sub);

    }

    public Page<Ordem> listAllPageName(String name, Pageable pageable) {
//        return repository.findAll(pageable);
        return repository.findByName(name, pageable);
    }




    public Ordem findByIdOrThrowRequestException(Long id) {
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

    public void replace(Ordem objeto) {
        Ordem categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(objeto);

    }

    public List<SaleByMonthDTO> findSaleByMonth(){
        return repository.findSaleByMonth();
    }

    public List<OrderByStatusDTO> findOrderByStatus(){
        return repository.findOrderByStatus();
    }
    public List<OrderByDayDTO> findOrderByDay(){
        return repository.findOrderByDay();
    }

}

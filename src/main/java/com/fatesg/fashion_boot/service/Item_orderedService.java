package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Item_Ordered;
import com.fatesg.fashion_boot.repository.Item_OrderedRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Item_orderedService {

    final Item_OrderedRepository repository;

    public Item_Ordered save(Item_Ordered ItemOrdered) {
        ItemOrdered.setId(null);
        return repository.save(ItemOrdered);
    }

    public Page<Item_Ordered> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Item_Ordered> listAll() {
        return repository.findAll();

    }


    public Item_Ordered findByIdOrThrowRequestException(Long id) {
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

    public void replace(Item_Ordered objeto) {
        Item_Ordered categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

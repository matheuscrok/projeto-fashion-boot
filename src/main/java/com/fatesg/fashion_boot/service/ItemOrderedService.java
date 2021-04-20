package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.ItemOrdered;
import com.fatesg.fashion_boot.repository.ItemOrderedRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrderedService {

    final ItemOrderedRepository repository;

    public ItemOrdered save(ItemOrdered ItemOrdered) {
        ItemOrdered.setId(null);
        return repository.save(ItemOrdered);
    }

    public Page<ItemOrdered> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<ItemOrdered> listAll() {
        return repository.findAll();

    }


    public ItemOrdered findByIdOrThrowRequestException(Long id) {
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

    public void replace(ItemOrdered objeto) {
        ItemOrdered categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(objeto);

    }

}

package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Address;
import com.fatesg.fashion_boot.repository.AddressRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    final AddressRepository repository;

    public Address save(Address address) {
        address.setId(null);
        return repository.save(address);
    }

    public Page<Address> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Address> listAll() {
        return repository.findAll();

    }


    public Address findByIdOrThrowRequestException(Long id) {
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

    public void replace(Address objeto) {
        Address categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

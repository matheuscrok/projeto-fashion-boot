package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Options;
import com.fatesg.fashion_boot.repository.OptionsRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionsService {

    final OptionsRepository repository;

    public Options save(Options options) {
        options.setId(null);
        return repository.save(options);
    }

    public Page<Options> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Options> listAll() {
        return repository.findAll();

    }


    public Options findByIdOrThrowRequestException(Long id) {
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

    public void replace(Options objeto) {
        Options categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(objeto);

    }

}

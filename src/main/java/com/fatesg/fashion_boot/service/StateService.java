package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.State;
import com.fatesg.fashion_boot.repository.StateRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    final StateRepository repository;

    public State save(State state) {
        state.setId(null);
        return repository.save(state);
    }

    public Page<State> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<State> listAll() {
        return repository.findAll();

    }


    public State findByIdOrThrowRequestException(Long id) {
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

    public void replace(State objeto) {
        State categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(objeto);

    }

}

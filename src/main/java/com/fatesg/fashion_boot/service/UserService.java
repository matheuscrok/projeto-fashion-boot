package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Usuario;
import com.fatesg.fashion_boot.repository.UserRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository repository;

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    public Page<Usuario> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Usuario> listAll() {
        return repository.findAll();

    }

    public Usuario findByIdOrThrowRequestException(String id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuario não localizado"));

    }


    public void delete(String id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a uma pessoa");
        }
    }

    public void replace(Usuario objeto) {
        Usuario categoriaSaved = findByIdOrThrowRequestException(objeto.getSub());
        repository.save(objeto);

    }

}

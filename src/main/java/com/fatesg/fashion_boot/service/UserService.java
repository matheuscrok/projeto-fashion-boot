package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.User;
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

    public User save(User user) {
        user.setId(null);
        return repository.save(user);
    }

    public Page<User> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<User> listAll() {
        return repository.findAll();

    }

    public User findByIdOrThrowRequestException(Long id) {
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

    public void replace(User objeto) {
        User categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

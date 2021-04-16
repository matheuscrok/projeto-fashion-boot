package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.City;
import com.fatesg.fashion_boot.repository.CityRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    final CityRepository repository;

    public City save(City city) {
        city.setId(null);
        return repository.save(city);
    }

    public Page<City> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<City> listAll() {
        return repository.findAll();

    }


    public City findByIdOrThrowRequestException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("cidade não localizada"));

    }


    public void delete(Long id) {
        findByIdOrThrowRequestException(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Não é posssivel apagar, pois está associada a uma pessoa");
        }
    }

    public void replace(City objeto) {
        City categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

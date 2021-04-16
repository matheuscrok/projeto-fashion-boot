package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.Form_Payment;
import com.fatesg.fashion_boot.repository.Form_PaymentRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Form_PaymentService {

    final Form_PaymentRepository repository;

    public Form_Payment save(Form_Payment form_payment) {
        form_payment.setId(null);
        return repository.save(form_payment);
    }

    public Page<Form_Payment> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Form_Payment> listAll() {
        return repository.findAll();

    }


    public Form_Payment findByIdOrThrowRequestException(Long id) {
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

    public void replace(Form_Payment objeto) {
        Form_Payment categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

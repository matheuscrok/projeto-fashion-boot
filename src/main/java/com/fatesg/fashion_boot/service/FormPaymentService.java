package com.fatesg.fashion_boot.service;

import com.fatesg.fashion_boot.entity.FormPayment;
import com.fatesg.fashion_boot.repository.FormPaymentRepository;
import com.fatesg.fashion_boot.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormPaymentService {

    final FormPaymentRepository repository;

    public FormPayment save(FormPayment form_payment) {
        form_payment.setId(null);
        return repository.save(form_payment);
    }

    public Page<FormPayment> listAllPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<FormPayment> listAll() {
        return repository.findAll();

    }


    public FormPayment findByIdOrThrowRequestException(Long id) {
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

    public void replace(FormPayment objeto) {
        FormPayment categoriaSaved = findByIdOrThrowRequestException(objeto.getId());
        repository.save(categoriaSaved);

    }

}

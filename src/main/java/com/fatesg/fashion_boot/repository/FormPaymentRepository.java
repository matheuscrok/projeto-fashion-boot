package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.FormPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormPaymentRepository extends JpaRepository<FormPayment, Long> {


}

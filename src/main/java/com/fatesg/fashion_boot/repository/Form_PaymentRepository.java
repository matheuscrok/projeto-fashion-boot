package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Form_Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Form_PaymentRepository extends JpaRepository<Form_Payment, Long> {

 
}

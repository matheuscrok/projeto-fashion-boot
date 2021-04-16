package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

 
}

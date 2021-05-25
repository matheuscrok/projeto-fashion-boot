package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByCategoryName(String name);

    List<Product> findProductByNameLike(String name);

    List<Product> findProductByBrandName(String name);

    @Query("select u from Product u where u.name like %:#{[0]}% and u.name like %:name%")
    Page<Product> findByName(String name, Pageable pageable);


}

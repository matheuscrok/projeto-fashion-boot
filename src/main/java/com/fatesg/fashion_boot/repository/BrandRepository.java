package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Brand;
import com.fatesg.fashion_boot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByName(String name);

    @Query("select u from Brand u where u.name like %:#{[0]}% and u.name like %:name%")
    Page<Brand> findByName(String name, Pageable pageable);
}

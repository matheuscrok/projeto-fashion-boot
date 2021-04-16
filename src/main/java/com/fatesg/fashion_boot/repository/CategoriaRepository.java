package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Category, Long> {
}

package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.ItemOrdered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemOrderedRepository extends JpaRepository<ItemOrdered, Long> {

 
}

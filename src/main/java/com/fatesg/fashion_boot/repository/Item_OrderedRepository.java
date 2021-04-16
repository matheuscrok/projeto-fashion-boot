package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Item_Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Item_OrderedRepository extends JpaRepository<Item_Ordered, Long> {

 
}

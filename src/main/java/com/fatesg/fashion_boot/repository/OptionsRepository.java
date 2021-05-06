package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {

    void deleteAllByProductId(Long id);

 
}

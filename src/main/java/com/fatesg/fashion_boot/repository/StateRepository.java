package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

 
}

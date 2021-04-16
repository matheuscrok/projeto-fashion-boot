package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

 
}

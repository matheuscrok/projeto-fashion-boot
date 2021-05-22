package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Brand;
import com.fatesg.fashion_boot.entity.Ordem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordem, Long> {

    @Query("select u from Ordem u where u.usuario.name like %:#{[0]}% and u.usuario.name like %:name%")
    Page<Ordem> findByName(String name, Pageable pageable);

    List<Ordem> findAllByUsuarioSub(String sub);
 
}

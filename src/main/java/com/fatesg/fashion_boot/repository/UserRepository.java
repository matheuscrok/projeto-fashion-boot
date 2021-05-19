package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String> {

 
}

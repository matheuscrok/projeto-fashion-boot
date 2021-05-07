package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mail;
    private String password;
    private String type;
    private String phone;

    @ManyToOne
    @JoinColumn
    private FormPayment form_payment;

    @ManyToOne
    @JoinColumn
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Ordem> ordemList = new ArrayList<>();

}

package com.fatesg.fashion_boot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

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
    private Form_Payment form_payment;


    @ManyToOne
    @JoinColumn
    private Address address;

}

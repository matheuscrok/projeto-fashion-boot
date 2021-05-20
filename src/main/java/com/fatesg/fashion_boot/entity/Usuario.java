package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Usuario {

    @Id
    private String sub;
    private String name;
    private String email;
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private FormPayment form_payment;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Address address;

}

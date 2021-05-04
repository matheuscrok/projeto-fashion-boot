package com.fatesg.fashion_boot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String road;
    private String number;
    private String complement;
    private String district;
    private String cep;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private City city;
}

package com.fatesg.fashion_boot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ItemOrdered {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;

    @ManyToOne
    @JoinColumn
    private Ordem ordem;

    @ManyToOne
    @JoinColumn
    private Product product;

}

package com.fatesg.fashion_boot.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.util.Date date_purchase;
    private String status;

    @ManyToOne
    @JoinColumn
    private User user;

}

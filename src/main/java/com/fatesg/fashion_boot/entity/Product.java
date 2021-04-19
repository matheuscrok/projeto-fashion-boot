package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Float price;
    private String photo;
    private String gender;
    private String size;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Brand brand;


}

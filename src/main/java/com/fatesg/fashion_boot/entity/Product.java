package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String detail;
    private Float consPrice;
    private Float salePrice;
    private String img;
    private String gender;
    private String size;
    private Boolean onSale;
    private Boolean inStock;



    @OneToMany(mappedBy = "product")
    List<GalleryImages> gallery = new ArrayList<>();


    @OneToMany(mappedBy = "product")
    List<Options> options = new ArrayList<>();

   // @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Category category;

  //  @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Brand brand;


}


@Entity
@Data
class GalleryImages{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Product product;



}

@Entity
@Data
class Options{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Product product;

}

package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
 //   @Length(min = 3, max = 1000)
    private String description;
 //   @Length(min = 3, max = 5000)
    private String detail;
    private Float consPrice;
    private Float salePrice;

//    @Length(min = 3, max = 5000)
    private String img;

    private String gender;
   // private String size;
    private Boolean onSale;
    private Boolean inStock;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    List<GalleryImages> gallery = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    List<Options> options = new ArrayList<>();

    // @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Category category;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Brand brand;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn
    private ItemOrdered itemOrdered;


}


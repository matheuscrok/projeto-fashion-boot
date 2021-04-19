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
    private Float price;
    private String photo;
    private String gender;
    private String size;


    @OneToMany(mappedBy = "product")
    List<GalleryImages> gallery = new ArrayList<>();

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

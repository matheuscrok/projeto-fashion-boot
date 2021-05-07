package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_purchase;
    private String status;
    private Float totalPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "ordem")
    List<ItemOrdered> itemOrdered = new ArrayList<>();


    @ManyToOne
    @JoinColumn
    private Usuario usuario;

}

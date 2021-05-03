package com.fatesg.fashion_boot.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "itemOrdered")
    private List<Product> product = new ArrayList<>();

}

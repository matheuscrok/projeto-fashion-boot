package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Ordem ordem;

}

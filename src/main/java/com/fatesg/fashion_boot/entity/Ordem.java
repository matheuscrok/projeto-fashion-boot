package com.fatesg.fashion_boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_purchase;
    private String status;
    private Long amount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private Usuario usuario;

}

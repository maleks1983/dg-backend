package com.app.dgBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;

    public Operation(String name, double price) {
        this.id = 0;
        this.name = name;
        this.price = price;
    }

    public Operation(int id, String name, double price) {
        this(name, price);
        this.id = id;
    }

    public Operation() {

    }


    public boolean isNew() {
        return id == 0;
    }
}

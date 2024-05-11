package com.example.savingsappbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double budget;
    @OneToOne
    private User user;
    @OneToMany
    private List<Transaction> transactionList;

    public Wallet(User user) {
        this.budget = 0.0;
        this.user = user;
        this.transactionList = new ArrayList<>();
    }

    public Wallet() {

    }
}

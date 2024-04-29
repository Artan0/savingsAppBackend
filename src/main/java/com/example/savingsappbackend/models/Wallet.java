package com.example.savingsappbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    public Wallet(Double budget, User user, List<Transaction> transactionList) {
        this.budget = budget;
        this.user = user;
        this.transactionList = transactionList;
    }

    public Wallet() {

    }
}

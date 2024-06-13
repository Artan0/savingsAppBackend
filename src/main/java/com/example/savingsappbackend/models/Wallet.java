package com.example.savingsappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double budget;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionList;

    public Wallet(User user) {
        this.budget = 0.0;
        this.user = user;
        this.transactionList = new ArrayList<>();
    }

    public Wallet() {

    }
}

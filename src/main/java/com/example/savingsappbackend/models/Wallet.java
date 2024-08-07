package com.example.savingsappbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double budget;
    private Double savingsBalance;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactionList;

    @ElementCollection
    @CollectionTable(name = "wallet_history", joinColumns = @JoinColumn(name = "wallet_id"))
    @MapKeyColumn(name = "history_date")
    @Column(name = "budget")
    private Map<LocalDate, Double> historyMap;

    public Wallet(User user) {
        this.budget = 0.0;
        this.savingsBalance = 0.0;
        this.user = user;
        this.transactionList = new ArrayList<>();
        this.historyMap = new HashMap<>();
    }

    public Wallet() {

    }

    public void decreaseBudget(Double amount) {
        if (this.budget >= amount) {
            this.budget -= amount;
        } else {
            throw new RuntimeException("Not Allowed");
        }
    }
    public void addHistory(LocalDate date, Double budget) {
        this.historyMap.put(date, budget);
    }

    public void increaseSavingsBalance(Double amount){
        this.savingsBalance+=amount;
    }
}

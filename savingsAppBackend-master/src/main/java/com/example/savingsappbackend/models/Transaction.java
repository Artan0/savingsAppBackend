package com.example.savingsappbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate date;
    private Double amount;
    private TransactionType type;

    public Transaction(String title, LocalDate date, Double amount, TransactionType type) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    public Transaction() {

    }
}

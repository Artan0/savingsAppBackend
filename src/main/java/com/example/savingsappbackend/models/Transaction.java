package com.example.savingsappbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate date;
    private Double amount;
    @Enumerated(EnumType.STRING)
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

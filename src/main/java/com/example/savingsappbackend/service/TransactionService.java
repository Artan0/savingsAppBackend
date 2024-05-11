package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.Transaction;
import com.example.savingsappbackend.models.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    /*
    private Long id;
    private String title;
    private LocalDate date;
    private Double amount;
    private TransactionType type;
     */
    public Transaction newTransaction(String title, LocalDate date, Double amount, TransactionType type);

    public List<Transaction> getMyTransactions(Long userId);
    public void deleteTransaction(Long transactionId);
}

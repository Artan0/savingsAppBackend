package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Transaction;
import com.example.savingsappbackend.models.TransactionType;
import com.example.savingsappbackend.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImplementation implements TransactionService {
    @Override
    public Transaction newTransaction(String title, LocalDate date, Double amount, TransactionType type) {
        return null;
    }

    @Override
    public List<Transaction> getMyTransactions(Long userId) {
        return null;
    }

    @Override
    public void deleteTransaction(Long transactionId) {

    }
}

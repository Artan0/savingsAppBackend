package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.Transaction;
import com.example.savingsappbackend.models.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Map;

public interface TransactionService {
    Transaction getById(Long transactionId);

    Page<Transaction> getAllTransactions(Long userId, Pageable pageable);

    Transaction createTransaction(String title, LocalDate date, Double amount, TransactionType type, Long userId);


    void deleteTransaction(Long transactionId);
     Map<String, Double> getTransactionSummaryByType(Long userId);

    }


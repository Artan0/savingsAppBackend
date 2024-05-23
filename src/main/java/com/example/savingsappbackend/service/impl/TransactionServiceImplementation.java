package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Transaction;
import com.example.savingsappbackend.models.TransactionType;
import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.exceptions.TransactionNotFoundException;
import com.example.savingsappbackend.repository.TransactionRepository;
import com.example.savingsappbackend.repository.UserRepository;
import com.example.savingsappbackend.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImplementation implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction newTransaction(String title, LocalDate date, Double amount, TransactionType type) {
        Transaction transaction = new Transaction(title, date, amount, type);
        this.transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getMyTransactions(Long userId) {
        List<Transaction> transactions = this.userRepository.getReferenceById(userId)
                .getWallet()
                .getTransactionList();

        return transactions;
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(TransactionNotFoundException::new);
        this.transactionRepository.delete(transaction);
    }
}

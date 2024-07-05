package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.*;
import com.example.savingsappbackend.models.exceptions.TransactionNotFoundException;
import com.example.savingsappbackend.repository.TransactionRepository;
import com.example.savingsappbackend.repository.UserRepository;
import com.example.savingsappbackend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionServiceImplementation(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction getById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(TransactionNotFoundException::new);
    }

    @Override
    public Page<Transaction> getAllTransactions(Long userId, Pageable pageable) {
        User user = this.userRepository.getReferenceById(userId);
        return this.transactionRepository.findByUser(user, pageable);
    }

    @Override
    public Transaction createTransaction(String title, LocalDate date, Double amount, TransactionType type, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Transaction transaction = new Transaction(title, date, amount, type,user);
        Wallet wallet = user.getWallet();

        if (type == TransactionType.EXPENSE) {
            wallet.setBudget(wallet.getBudget() - amount);
        } else if (type == TransactionType.INCOME) {
            wallet.setBudget(wallet.getBudget() + amount);
        }
        userRepository.save(user);
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = getById(transactionId);
        transactionRepository.delete(transaction);
    }
}

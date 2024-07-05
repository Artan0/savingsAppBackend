package com.example.savingsappbackend.repository;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.Transaction;
import com.example.savingsappbackend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    Page<Transaction> findByUser(User owner, Pageable pageable);

    Page<Transaction> findByUserAndTitleContainingIgnoreCase(User owner, String title, Pageable pageable);

}

package com.example.savingsappbackend.repository;

import com.example.savingsappbackend.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
}

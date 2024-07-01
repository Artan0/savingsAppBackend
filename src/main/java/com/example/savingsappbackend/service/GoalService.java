package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface GoalService {
    public Goal getById(Long goalId);
    public Page<Goal> getAllGoals(Long userId, String search, Pageable pageable) ;
    public Goal newGoal(Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long userId, Double savingsAmount, String savingsPeriod);
    public Goal editGoal(Long goalId, Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Double savingsAmount, String savingsPeriod);
    public void deleteGoal(Long goalId);
}

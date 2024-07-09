package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.dto.WeeklyGoalStats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface GoalService {
    Goal getById(Long goalId);
    Page<Goal> getAllGoals(Long userId, String search, Pageable pageable) ;
    Goal newGoal(Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long userId, Double savingsAmount, String savingsPeriod);
    Goal editGoal(Long goalId, Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Double savingsAmount, String savingsPeriod);
    void deleteGoal(Long goalId);
    List<WeeklyGoalStats> getWeeklyGoalStats(Long userId);
    }

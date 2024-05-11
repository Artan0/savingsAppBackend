package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.service.GoalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GoalServiceImplementation implements GoalService{
    @Override
    public Goal getById(Long goalId) {
        return null;
    }

    @Override
    public List<Goal> getAllGoals(Long userId) {
        return null;
    }

    @Override
    public Goal newGoal(Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long userId) {
        return null;
    }

    @Override
    public Goal editGoal(Long goalId, Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description) {
        return null;
    }

    @Override
    public void deleteGoal(Long goalId) {

    }
}

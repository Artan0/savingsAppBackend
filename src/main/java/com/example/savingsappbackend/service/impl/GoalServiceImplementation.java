package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.models.exceptions.GoalNotFoundException;
import com.example.savingsappbackend.repository.GoalRepository;
import com.example.savingsappbackend.repository.UserRepository;
import com.example.savingsappbackend.service.GoalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImplementation implements GoalService{
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalServiceImplementation(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Goal getById(Long goalId) {
        return this.goalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
    }

    @Override
    public List<Goal> getAllGoals(Long userId, int page, int pageSize) {
        List<Goal> goals = this.userRepository.getReferenceById(userId).getGoalList();
        int skip = (page - 1) * pageSize;
        return goals.stream().skip(skip).limit(pageSize).collect(Collectors.toList());
    }


    @Override
    public Goal newGoal(Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(GoalNotFoundException::new);
        Goal goal = new Goal(currentAmt, targetAmt, title, targetDate, description, user);
        this.goalRepository.save(goal);
        return goal;
    }


    @Override
    public Goal editGoal(Long goalId, Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description) {
        Goal goal = this.goalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);

        goal.setCurrentAmount(currentAmt);
        goal.setTargetAmount(targetAmt);
        goal.setTitle(title);
        goal.setTargetDate(targetDate);
        goal.setDescription(description);

        this.goalRepository.save(goal);

        return goal;
    }

    @Override
    public void deleteGoal(Long goalId) {
        Goal goal = this.goalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
        this.goalRepository.delete(goal);
    }
}

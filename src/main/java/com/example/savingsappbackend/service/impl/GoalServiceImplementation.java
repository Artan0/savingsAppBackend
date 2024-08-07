package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.models.dto.WeeklyGoalStats;
import com.example.savingsappbackend.models.exceptions.GoalNotFoundException;
import com.example.savingsappbackend.repository.GoalRepository;
import com.example.savingsappbackend.repository.UserRepository;
import com.example.savingsappbackend.service.GoalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
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

    public Page<Goal> getAllGoals(Long userId, String search, Pageable pageable) {
        User user = this.userRepository.getReferenceById(userId);
        if (search == null || search.isEmpty()) {
            return this.goalRepository.findByOwner(user, pageable);
        } else {
            return this.goalRepository.findByOwnerAndTitleContainingIgnoreCase(user, search, pageable);
        }
    }


    @Override
    public Goal newGoal(Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long userId, Double savingsAmount, String savingsPeriod) {
        User user = userRepository.findById(userId).orElseThrow(GoalNotFoundException::new);
        Wallet wallet = user.getWallet();

        wallet.decreaseBudget(currentAmt);
        wallet.increaseSavingsBalance(currentAmt);
        userRepository.save(user);

        Goal goal = new Goal(currentAmt, targetAmt, savingsAmount, savingsPeriod, title, targetDate, description, user);
        this.goalRepository.save(goal);
        return goal;
    }



    @Override
    public Goal editGoal(Long goalId, Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Double savingsAmount, String savingsPeriod) {
        Goal goal = this.goalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);

        goal.setCurrentAmount(currentAmt);
        goal.setTargetAmount(targetAmt);
        goal.setTitle(title);
        goal.setTargetDate(targetDate);
        goal.setDescription(description);
        goal.setSavingsAmount(savingsAmount);
        goal.setSavingsPeriod(savingsPeriod);
        this.goalRepository.save(goal);

        return goal;
    }

    @Override
    public void deleteGoal(Long goalId) {
        Goal goal = this.goalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);
        this.goalRepository.delete(goal);
    }

    @Override
    public List<WeeklyGoalStats> getWeeklyGoalStats(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(GoalNotFoundException::new);
        List<Goal> userGoals = this.goalRepository.findGoalsByOwner(user);

        LocalDate now = LocalDate.now();
        LocalDate[] weeksStartDates = {
                now.minusWeeks(3).with(java.time.DayOfWeek.MONDAY),
                now.minusWeeks(2).with(java.time.DayOfWeek.MONDAY),
                now.minusWeeks(1).with(java.time.DayOfWeek.MONDAY),
                now.with(java.time.DayOfWeek.MONDAY)
        };

        return Arrays.stream(weeksStartDates)
                .map(weekStart -> {
                    LocalDate weekEnd = weekStart.plusDays(6);
                    long totalGoals = userGoals.stream().filter(goal -> !goal.getTargetDate().isBefore(weekStart) && !goal.getTargetDate().isAfter(weekEnd)).count();
                    long completedGoals = userGoals.stream().filter(goal -> goal.isCompleted() && !goal.getTargetDate().isBefore(weekStart) && !goal.getTargetDate().isAfter(weekEnd)).count();
                    long overdueGoals = userGoals.stream().filter(goal -> goal.isOverdued() && goal.getTargetDate().isBefore(weekEnd) && goal.getTargetDate().isAfter(weekStart)).count();

                    WeeklyGoalStats stats = new WeeklyGoalStats();
                    stats.setPeriod(weekStart.toString() + " - " + weekEnd.toString());
                    stats.setTotalGoals(totalGoals);
                    stats.setCompletedGoals(completedGoals);
                    stats.setOverdueGoals(overdueGoals);
                    return stats;
                })
                .collect(Collectors.toList());
    }
}

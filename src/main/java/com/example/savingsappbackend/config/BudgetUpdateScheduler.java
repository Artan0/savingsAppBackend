package com.example.savingsappbackend.config;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.repository.GoalRepository;
import com.example.savingsappbackend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BudgetUpdateScheduler {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public BudgetUpdateScheduler(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateBudgets() {
        List<Goal> goals = goalRepository.findAll();
        for (Goal goal : goals) {
            LocalDateTime now = LocalDateTime.now();
            String period = goal.getSavingsPeriod();

            if (shouldUpdateBudget(now, goal.getLastUpdated().atStartOfDay(), period)) {
                Wallet wallet = goal.getOwner().getWallet();
                wallet.decreaseBudget(goal.getSavingsAmount());
                goal.increaseCurrentAmount(goal.getSavingsAmount());
                userRepository.save(goal.getOwner());
                goal.setLastUpdated(LocalDate.from(now));
                goalRepository.save(goal);
            }
        }
    }

    private boolean shouldUpdateBudget(LocalDateTime now, LocalDateTime lastUpdated, String period) {
        return switch (period.toLowerCase()) {
            case "minute" -> now.minusMinutes(1).isAfter(lastUpdated);
            case "daily" -> now.toLocalDate().isAfter(lastUpdated.toLocalDate());
            case "weekly" -> now.toLocalDate().minusWeeks(1).isAfter(lastUpdated.toLocalDate());
            case "monthly" -> now.toLocalDate().minusMonths(1).isAfter(lastUpdated.toLocalDate());
            default -> false;
        };
    }
}

package com.example.savingsappbackend.config;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.repository.GoalRepository;
import com.example.savingsappbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void updateBudgets() {
        List<Goal> goals = goalRepository.findAll();
        for (Goal goal : goals) {
            LocalDateTime now = LocalDateTime.now();
            String period = goal.getSavingsPeriod();

            if (shouldUpdateBudget(now, goal.getLastUpdated(), period) && canUpdateGoal(goal)) {
                User user = userRepository.findByIdWithWallet(goal.getOwner().getId()).orElse(null);

                if (user == null || user.getWallet() == null) {
                    continue;
                }

                Wallet wallet = user.getWallet();
                wallet.decreaseBudget(goal.getSavingsAmount());
                goal.increaseCurrentAmount(goal.getSavingsAmount());
                userRepository.save(user);
                goal.setLastUpdated(now);
                goalRepository.save(goal);
            }
        }
    }

    private boolean shouldUpdateBudget(LocalDateTime now, LocalDateTime lastUpdated, String period) {
        if (period == null) {
            return false;
        }
        return switch (period.toLowerCase()) {
            case "minute" -> now.minusMinutes(1).isAfter(lastUpdated);
            case "daily" -> now.toLocalDate().isAfter(lastUpdated.toLocalDate());
            case "weekly" -> now.toLocalDate().minusWeeks(1).isAfter(lastUpdated.toLocalDate());
            case "monthly" -> now.toLocalDate().minusMonths(1).isAfter(lastUpdated.toLocalDate());
            default -> false;
        };
    }

    private boolean canUpdateGoal(Goal goal) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate targetDate = goal.getTargetDate();

        return (goal.getCurrentAmount() < goal.getTargetAmount()) && (targetDate == null || now.toLocalDate().isBefore(targetDate));
    }
}

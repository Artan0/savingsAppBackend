package com.example.savingsappbackend.config;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.TransactionType;
import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.repository.GoalRepository;
import com.example.savingsappbackend.repository.UserRepository;
import com.example.savingsappbackend.service.NotificationService;
import com.example.savingsappbackend.service.TransactionService;
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
    private final TransactionService transactionService;
    private final NotificationService notificationService;

    public BudgetUpdateScheduler(GoalRepository goalRepository, UserRepository userRepository,
                                 TransactionService transactionService, NotificationService notificationService) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.notificationService = notificationService;
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

//                ALTER TABLE transactions
//                DROP CONSTRAINT IF EXISTS transactions_type_check;
//
//                ALTER TABLE transactions
//                ADD CONSTRAINT transactions_type_check
//                CHECK (type IN ('INCOME', 'EXPENSE', 'SAVINGS'));

                Wallet wallet = user.getWallet();
                wallet.decreaseBudget(goal.getSavingsAmount());
                wallet.increaseSavingsBalance(goal.getSavingsAmount());
                goal.increaseCurrentAmount(goal.getSavingsAmount());
                userRepository.save(user);
                goal.setLastUpdated(now);
                goalRepository.save(goal);
                transactionService.createTransaction("Savings", LocalDate.now(), goal.getSavingsAmount(), TransactionType.SAVINGS, user.getId());

                if (goal.getCurrentAmount() >= goal.getTargetAmount()) {
                    goal.setCompleted(true);
                    goalRepository.save(goal);
                    notificationService.createNotification("Goal " + goal.getTitle() + " successfully completed.", user.getId());
                }
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

        if (goal.getCurrentAmount() < goal.getTargetAmount()) {
            if (targetDate == null || now.toLocalDate().isBefore(targetDate)) {
                return true;
            } else {
                goal.setOverdued(true);
                return false;
            }
        } else {
            goal.setCompleted(true);
            return false;
        }
    }
}

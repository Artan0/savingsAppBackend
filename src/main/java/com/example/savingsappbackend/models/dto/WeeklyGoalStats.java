package com.example.savingsappbackend.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeeklyGoalStats {
    private String period;
    private long totalGoals;
    private long completedGoals;
    private long overdueGoals;
}
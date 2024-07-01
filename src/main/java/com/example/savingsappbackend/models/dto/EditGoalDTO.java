package com.example.savingsappbackend.models.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EditGoalDTO {
    public Long goalId;
    public Double currentAmount;
    public Double targetAmount;
    public String title;
    public LocalDate targetDate;
    public String description;
    public Long id;
    public Double savingsAmount;
    public String savingsPeriod;

    public EditGoalDTO(Long goalId, Double currentAmount, Double targetAmount, String title, LocalDate targetDate, String description, Long id, Double savingsAmount, String savingsPeriod) {
        this.goalId = goalId;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.title = title;
        this.targetDate = targetDate;
        this.description = description;
        this.id = id;
        this.savingsAmount = savingsAmount;
        this.savingsPeriod = savingsPeriod;
    }
}

package com.example.savingsappbackend.models.dto;

import java.time.LocalDate;

public class EditGoalDTO {
    public Long goalId;
    public Double currentAmt;
    public Double targetAmt;
    public String title;
    public LocalDate targetDate;
    public String description;
    public Long id;

    public EditGoalDTO(Long goalId, Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long id) {
        this.goalId = goalId;
        this.currentAmt = currentAmt;
        this.targetAmt = targetAmt;
        this.title = title;
        this.targetDate = targetDate;
        this.description = description;
        this.id = id;
    }
}

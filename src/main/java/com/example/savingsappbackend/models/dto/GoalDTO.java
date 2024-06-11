package com.example.savingsappbackend.models.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class GoalDTO {

    public Double currentAmt;
    public Double targetAmt;
    public String title;
    public LocalDate targetDate;
    public String description;
    public Long id;

    public GoalDTO(Double currentAmt, Double targetAmt, String title, LocalDate targetDate, String description, Long id) {
        this.currentAmt = currentAmt;
        this.targetAmt = targetAmt;
        this.title = title;
        this.targetDate = targetDate;
        this.description = description;
        this.id = id;
    }
}

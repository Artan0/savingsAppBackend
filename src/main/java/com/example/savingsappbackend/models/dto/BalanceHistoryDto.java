package com.example.savingsappbackend.models.dto;

import lombok.Data;


import java.time.LocalDate;

@Data
public class BalanceHistoryDto {
    private LocalDate date;
    private Double budget;

    public BalanceHistoryDto(LocalDate date, Double budget) {
        this.date = date;
        this.budget = budget;
    }

}

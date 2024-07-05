package com.example.savingsappbackend.models.dto;

import com.example.savingsappbackend.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionDTO {
    private String title;
    private LocalDate date;
    private Double amount;
    private TransactionType type;
    private Long userId;
}

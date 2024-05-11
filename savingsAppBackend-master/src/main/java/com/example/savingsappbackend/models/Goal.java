package com.example.savingsappbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double currentAmount;
    private Double targetAmount;
    private String title;
    private LocalDate targetDate;
    private String description;

    public Goal(){

    }
    public Goal(Double currentAmount, Double targetAmount, String title, LocalDate targetDate, String description) {
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.title = title;
        this.targetDate = targetDate;
        this.description = description;
    }

}

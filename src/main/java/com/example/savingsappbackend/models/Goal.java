package com.example.savingsappbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double currentAmount;
    private Double targetAmount;
    private Double savingsAmount;
    private String savingsPeriod;
    private String title;
    private LocalDate targetDate;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
    private LocalDate lastUpdated;



    public Goal(){

    }

    public Goal(Double currentAmount, Double targetAmount, Double savingsAmount, String savingsPeriod, String title, LocalDate targetDate, String description, User owner) {
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.savingsAmount = savingsAmount;
        this.savingsPeriod = savingsPeriod;
        this.title = title;
        this.targetDate = targetDate;
        this.description = description;
        this.owner = owner;
    }
    public void increaseCurrentAmount(Double amount) {
        this.currentAmount += amount;
    }

}

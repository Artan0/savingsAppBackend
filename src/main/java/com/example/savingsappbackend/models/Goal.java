package com.example.savingsappbackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;
    private LocalDateTime lastUpdated;
    private boolean isCompleted;
    private boolean isOverdued;



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
        this.lastUpdated = LocalDateTime.now();
        this.isCompleted = Objects.equals(this.targetAmount, currentAmount);
        this.isOverdued = this.targetDate.isBefore(LocalDate.now());

    }
    public void increaseCurrentAmount(Double amount) {
        this.currentAmount += amount;
    }

}

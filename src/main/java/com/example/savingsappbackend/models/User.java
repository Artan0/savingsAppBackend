package com.example.savingsappbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Boolean isEmployed;
    private Long phoneNumber;
    @OneToOne
    private Wallet wallet;
    @OneToMany
    private List<Goal> goalList;

    public User(String firstName, String lastName, String email, LocalDate dateOfBirth, Boolean isEmployed, Long phoneNumber, Wallet wallet, List<Goal> goalList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.isEmployed = isEmployed;
        this.phoneNumber = phoneNumber;
        this.wallet = wallet;
        this.goalList = goalList;
    }


    public User() {

    }
}

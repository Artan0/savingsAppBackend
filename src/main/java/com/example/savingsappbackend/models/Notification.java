package com.example.savingsappbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timestamp;
    private Long userId;
    private boolean read;

    public Notification() {
    }

    public Notification(String message, Long userId) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.userId = userId;
    }
}

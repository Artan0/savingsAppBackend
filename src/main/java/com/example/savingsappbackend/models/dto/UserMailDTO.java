package com.example.savingsappbackend.models.dto;

public class UserMailDTO {
    public String email;
    public String message;

    public UserMailDTO(String email, String message) {
        this.email = email;
        this.message = message;
    }

    public String getUserEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}

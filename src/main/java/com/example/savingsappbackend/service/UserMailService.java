package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.UserMail;


public interface UserMailService {
    public UserMail addNewEmail (String userEmail, String message);
}

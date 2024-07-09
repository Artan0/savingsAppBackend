package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.UserMail;


public interface UserMailService {
    UserMail addNewEmail (String userEmail, String message);
}

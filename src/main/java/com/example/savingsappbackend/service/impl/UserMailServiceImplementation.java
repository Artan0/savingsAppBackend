package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.UserMail;
import com.example.savingsappbackend.repository.UserMailRepository;
import com.example.savingsappbackend.service.UserMailService;
import org.springframework.stereotype.Service;

@Service
public class UserMailServiceImplementation implements UserMailService {
    private final UserMailRepository userMailRepository;

    public UserMailServiceImplementation(UserMailRepository userMailRepository) {
        this.userMailRepository = userMailRepository;
    }

    @Override
    public UserMail addNewEmail(String userEmail, String message) {
        UserMail userMail = new UserMail(userEmail, message);
        this.userMailRepository.save(userMail);
        return userMail;
    }
}

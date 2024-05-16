package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.dto.CredentialsDto;
import com.example.savingsappbackend.models.dto.SignUpDto;
import com.example.savingsappbackend.models.dto.UserDto;

public interface UserService {
    UserDto login(CredentialsDto credentialsDto);
    UserDto register(SignUpDto userDto);
    UserDto findByEmail(String login);
}

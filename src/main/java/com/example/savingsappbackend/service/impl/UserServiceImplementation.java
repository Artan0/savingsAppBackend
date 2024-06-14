package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.mappers.UserMapper;
import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.User;
import com.example.savingsappbackend.models.Wallet;
import com.example.savingsappbackend.models.dto.CredentialsDto;
import com.example.savingsappbackend.models.dto.SignUpDto;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.models.exceptions.AppException;
import com.example.savingsappbackend.repository.UserRepository;
import com.example.savingsappbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        Wallet wallet = new Wallet(user);
        List<Goal> goalList = new ArrayList<>();

        user.setGoalList(goalList);
        user.setWallet(wallet);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByEmail(String login) {
        User user = userRepository.findByEmail(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto updateProfile(Long userId, String firstName, String lastName, String email, LocalDate dateOfBirth, Long phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setDateOfBirth(dateOfBirth);
        User updatedUser = userRepository.save(user);
        return userMapper.toUserDto(updatedUser);
    }


}

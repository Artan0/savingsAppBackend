package com.example.savingsappbackend.web;

import com.example.savingsappbackend.config.UserAuthenticationProvider;
import com.example.savingsappbackend.models.dto.CredentialsDto;
import com.example.savingsappbackend.models.dto.SignUpDto;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getEmail()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
    @GetMapping("/user-info")
    public ResponseEntity<UserDto> getUserInfo(HttpServletRequest request) {
        String email = userAuthenticationProvider.getEmailFromToken(request.getHeader("Authorization"));
        UserDto userDto = userService.findByEmail(email);
        return ResponseEntity.ok(userDto);
    }

}

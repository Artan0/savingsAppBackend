package com.example.savingsappbackend.web;

import com.example.savingsappbackend.config.UserAuthenticationProvider;
import com.example.savingsappbackend.models.Notification;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.service.NotificationService;
import com.example.savingsappbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserAuthenticationProvider userAuthenticationProvider, UserService userService) {
        this.notificationService = notificationService;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userService = userService;
    }


    @GetMapping("/notification")
    public List<Notification> getUserNotifications(@RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        return notificationService.getUserNotifications(user.getId());
    }

    @PostMapping("/markAsRead")
    public void markNotificationsAsRead(@RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        notificationService.markNotificationsAsRead(user.getId());
    }
}

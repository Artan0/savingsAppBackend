package com.example.savingsappbackend.service;

import com.example.savingsappbackend.models.Notification;

import java.util.List;

public interface NotificationService {
    void markNotificationsAsRead(Long userId);
    List<Notification> getUserNotifications(Long userId);
    void createNotification(String message, Long userId) ;

    }

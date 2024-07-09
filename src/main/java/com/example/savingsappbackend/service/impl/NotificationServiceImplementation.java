package com.example.savingsappbackend.service.impl;

import com.example.savingsappbackend.models.Notification;
import com.example.savingsappbackend.repository.NotificationRepository;
import com.example.savingsappbackend.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImplementation implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImplementation(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(String message, Long userId) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }

    public void markNotificationsAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalse(userId);
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
}

package com.brokingapp.service;

import com.brokingapp.model.Notification;
import com.brokingapp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification sendNotification(Long userId, String type, String message) {
        Notification notification = new Notification(null, userId, type, message, false, LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
} 
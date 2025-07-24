package com.brokingapp.controller;

import com.brokingapp.model.Notification;
import com.brokingapp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public Notification sendNotification(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        String type = body.get("type").toString();
        String message = body.get("message").toString();
        return notificationService.sendNotification(userId, type, message);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }
} 
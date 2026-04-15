package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.User;
import com.example.demo.services.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    // 🔔 Get all
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAll(@PathVariable Integer userId){
        return notificationService.getAllNotifications(userId);
    }

    // 🔔 Get unread
    @GetMapping("/unread/{userId}")
    public ResponseEntity<?> getUnread(@PathVariable Integer userId){
        return notificationService.getUnreadNotifications(userId);
    }

    // ✅ Mark as read
    @PutMapping("/read/{id}")
    public ResponseEntity<?> markRead(@PathVariable Integer id){
        return notificationService.markAsRead(id);
    }

    // ❌ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        return notificationService.deleteNotification(id);
    }
   
}

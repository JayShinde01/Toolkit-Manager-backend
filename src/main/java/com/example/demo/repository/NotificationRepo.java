package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Notification;
import com.example.demo.enums.NotificationStatus;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserId(Integer userId);

    List<Notification> findByUserIdAndStatus(Integer userId, NotificationStatus status);
}
package com.example.demo.Entities;

import java.time.LocalDateTime;

import com.example.demo.enums.NotificationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    private String message;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;// READ / UNREAD

    private LocalDateTime createdAt;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public NotificationStatus getStatus() {
		return status;
	}


	public void setStatus(NotificationStatus status) {
		this.status = status;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public Notification(Integer id, User user, String message, NotificationStatus status, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.user = user;
		this.message = message;
		this.status = status;
		this.createdAt = createdAt;
	}


	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}

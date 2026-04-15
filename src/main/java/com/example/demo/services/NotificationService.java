package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Notification;
import com.example.demo.Entities.User;
import com.example.demo.enums.NotificationStatus;
import com.example.demo.repository.NotificationRepo;

@Service
public class NotificationService {

	@Autowired
	NotificationRepo notificationrepo;
	
	public void createNotification(User user, String message){

	    Notification n = new Notification();
	    n.setUser(user);
	    n.setMessage(message);
	    n.setStatus(NotificationStatus.UNREAD);
	    n.setCreatedAt(LocalDateTime.now());

	    notificationrepo.save(n);
	}
	public ResponseEntity<?> getAllNotifications(Integer userId){

	    List<Notification> list = notificationrepo.findByUserId(userId);

	    if(list.isEmpty()){
	        return ResponseEntity.ok("No notifications");
	    }

	    return ResponseEntity.ok(list);
	}
	public ResponseEntity<?> getUnreadNotifications(Integer userId){

	    List<Notification> list = notificationrepo
	            .findByUserIdAndStatus(userId, NotificationStatus.UNREAD);

	    return ResponseEntity.ok(list);
	}
	public ResponseEntity<?> markAsRead(Integer notificationId){

	    Notification n = notificationrepo.findById(notificationId)
	            .orElseThrow(() -> new RuntimeException("Notification not found"));

	    n.setStatus(NotificationStatus.READ);

	    notificationrepo.save(n);

	    return ResponseEntity.ok("Marked as read");
	}
	public ResponseEntity<?> deleteNotification(Integer id){

	    if(!notificationrepo.existsById(id)){
	        return ResponseEntity.notFound().build();
	    }

	    notificationrepo.deleteById(id);

	    return ResponseEntity.ok("Deleted successfully");
	}
}

//package com.example.demo.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.Entities.User;
//import com.example.demo.repository.userRepo;
//
//@Service
//public class userServices {
//	@Autowired
//	userRepo userrepo;
//	public ResponseEntity<?> registerUser(User user){
//		try {
//			User createdUser = userrepo.save(user);
//			return ResponseEntity.status(201).body(createdUser);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(500).body("Something error to create user"+e.getMessage());
//		}
//		
//		
//	}
//	public ResponseEntity<?> loginUser(User user){
//		try {
//			User existingUser = userrepo.save(user);
//			return ResponseEntity.status(201).body(createdUser);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(500).body("Something error to create user"+e.getMessage());
//		}
//		
//		
//	}
//
//}

package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.User;
import com.example.demo.repository.userRepo;
import com.example.demo.security.JwtUtil;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private userRepo userrepo;

    @Autowired
    private JwtUtil jwtUtil;

    // --- 1. REGISTRATION ENDPOINT ---
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // In a real app, you would check if the username already exists here first!
            User createdUser = userrepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user: " + e.getMessage());
        }
    }

    // --- 2. LOGIN ENDPOINT ---

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) { 
        try {
            // 1. Search the database using the username provided in the request
            User existingUser = userrepo.findByUserName(loginData.getUserName());
            
            // 2. Check if the user was actually found in the database
            if (existingUser != null) {
                
                // 3. Compare the database password to the request password
                if (existingUser.getPassword().equals(loginData.getPassword())) {
                    
                    // Passwords match! Generate the token
                    String token = jwtUtil.generateToken(existingUser.getUserName(),existingUser.getRole().name());
                    return ResponseEntity.ok().body(token);
                    
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Password");
                }
                
            } else {
                // This 'else' now correctly pairs with the 'if (existingUser != null)' check
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during login: " + e.getMessage());
        }
    }
}
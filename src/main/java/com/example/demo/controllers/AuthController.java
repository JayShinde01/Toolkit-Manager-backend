package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Admin;
import com.example.demo.Entities.LabAssistant;
import com.example.demo.Entities.Student;
import com.example.demo.Entities.User;
import com.example.demo.enums.UserRole;
import com.example.demo.repository.AdminRepo;
import com.example.demo.repository.LabAssistantRepo;
import com.example.demo.repository.StudentRepo;
import com.example.demo.repository.userRepo;
import com.example.demo.security.JwtUtil;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private userRepo userrepo;
    @Autowired
    private LabAssistantRepo labassistantrepo;
    
    @Autowired
    private StudentRepo studentrepo;
    
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private JwtUtil jwtUtil;

    // --- 1. REGISTRATION ENDPOINT ---
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {

            // 🔥 STUDENT FLOW
            if (user.getRole() == UserRole.STUDENT) {

                Student student = new Student();

                // ✅ map USER fields → STUDENT
                student.setUserName(user.getUserName());
                student.setPassword(user.getPassword());
                student.setEmail(user.getEmail());
                student.setPhone(user.getPhone());
                student.setRole(UserRole.STUDENT);


                Student saved = studentrepo.save(student);

                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }

            // 🔥 LAB ASSISTANT FLOW
            else if (user.getRole() == UserRole.LAB_ASSISTANT) {

                LabAssistant lab = new LabAssistant();

                lab.setUserName(user.getUserName());
                lab.setPassword(user.getPassword());
                lab.setEmail(user.getEmail());
                lab.setPhone(user.getPhone());
                lab.setRole(UserRole.LAB_ASSISTANT);

                LabAssistant saved = labassistantrepo.save(lab);

                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }

            // 🔥 ADMIN FLOW
            else {

                Admin admin = new Admin();

                admin.setUserName(user.getUserName());
                admin.setPassword(user.getPassword());
                admin.setEmail(user.getEmail());
                admin.setPhone(user.getPhone());
                admin.setRole(UserRole.ADMIN);

                Admin saved = adminRepo.save(admin);

                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
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
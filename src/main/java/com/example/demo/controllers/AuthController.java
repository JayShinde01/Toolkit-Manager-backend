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

import java.util.HashMap;
import java.util.Map;
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
            User existingUser = userrepo.findByUserName(loginData.getUserName());

            if (existingUser != null) {

                if (existingUser.getPassword().equals(loginData.getPassword())) {

                    String token = jwtUtil.generateToken(
                            existingUser.getUserName(),
                            existingUser.getRole().name()
                    );

                    // ✅ RETURN PROPER JSON RESPONSE
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", token);
                    response.put("userId", existingUser.getId());
                    response.put("userName", existingUser.getUserName());
                    response.put("role", existingUser.getRole());

                    return ResponseEntity.ok(response);

                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Invalid Password");
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during login: " + e.getMessage());
        }
    }
}
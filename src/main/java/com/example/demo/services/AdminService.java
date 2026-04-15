package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Admin;
import com.example.demo.repository.AdminRepo;
import com.example.demo.repository.userRepo;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private userRepo userrepo;

    public ResponseEntity<?> updateAdmin(Integer id, Admin updatedData) {

        try {
            Admin existing = adminRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            if (updatedData.getUserName() != null)
                existing.setUserName(updatedData.getUserName());

            if (updatedData.getPassword() != null)
                existing.setPassword(updatedData.getPassword());

            if (updatedData.getEmail() != null)
                existing.setEmail(updatedData.getEmail());

            if (updatedData.getPhone() != null)
                existing.setPhone(updatedData.getPhone());

            if (updatedData.getDepartment() != null)
                existing.setDepartment(updatedData.getDepartment());

            Admin saved = adminRepo.save(existing);

            return new ResponseEntity<>(saved, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteAdmin(Integer id) {

        try {
            Admin admin = adminRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            adminRepo.delete(admin);

            return new ResponseEntity<>("Admin deleted successfully", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

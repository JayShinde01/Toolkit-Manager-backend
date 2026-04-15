package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.LabAssistant;
import com.example.demo.repository.LabAssistantRepo;
import com.example.demo.repository.userRepo;
@Service
public class LabAssistantService {

    @Autowired
    private LabAssistantRepo labRepo;

    @Autowired
    private userRepo userRepo;

    public ResponseEntity<?> updateLabAssistant(Integer id, LabAssistant updatedData) {

        try {
            LabAssistant existing = labRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lab Assistant not found"));

            if (updatedData.getUserName() != null)
                existing.setUserName(updatedData.getUserName());

            if (updatedData.getPassword() != null)
                existing.setPassword(updatedData.getPassword());

            if (updatedData.getEmail() != null)
                existing.setEmail(updatedData.getEmail());

            if (updatedData.getPhone() != null)
                existing.setPhone(updatedData.getPhone());

            if (updatedData.getLabName() != null)
                existing.setLabName(updatedData.getLabName());

            if (updatedData.getShift() != null)
                existing.setShift(updatedData.getShift());

            if (updatedData.getEmployeeId() != null)
                existing.setEmployeeId(updatedData.getEmployeeId());

            LabAssistant saved = labRepo.save(existing);

            return new ResponseEntity<>(saved, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteLabAssistant(Integer id) {

        try {
            LabAssistant lab = labRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Lab Assistant not found"));

            labRepo.delete(lab);

            return new ResponseEntity<>("Lab Assistant deleted successfully", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

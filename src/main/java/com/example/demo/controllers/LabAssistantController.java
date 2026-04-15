package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.LabAssistant;
import com.example.demo.services.LabAssistantService;

@RestController
@RequestMapping("/api/lab")
public class LabAssistantController {

    @Autowired
    private LabAssistantService service;

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody LabAssistant lab, @PathVariable Integer id) {
        return service.updateLabAssistant(id, lab);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return service.deleteLabAssistant(id);
    }
}

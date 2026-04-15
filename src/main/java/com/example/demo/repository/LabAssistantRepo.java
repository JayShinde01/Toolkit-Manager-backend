package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.LabAssistant;

public interface LabAssistantRepo extends JpaRepository<LabAssistant, Integer> {

    Optional<LabAssistant> findByEmployeeId(String employeeId);

}
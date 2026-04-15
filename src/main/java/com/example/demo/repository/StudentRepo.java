package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Student;

public interface StudentRepo extends JpaRepository<Student, Integer> {
	Optional<Student> findByPrn(String prn); 
}

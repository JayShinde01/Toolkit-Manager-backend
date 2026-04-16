package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Student;
import com.example.demo.services.StudentService;

@RestController
@RequestMapping("/api/student")
@CrossOrigin("*")
public class StudentController {
	
	@Autowired
	StudentService studentservice;
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateStudent(@RequestBody Student s, @PathVariable Integer userId){
//		System.out.print(userId+"in controller");
		return studentservice.updateStudent(userId, s);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer userId){
		return studentservice.deleteStudent(userId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?>getStudentById(@PathVariable Integer id){
		return studentservice.getStudentById(id);
	}
	

}

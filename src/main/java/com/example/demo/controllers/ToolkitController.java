package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Toolkit;
import com.example.demo.services.ToolkitServices;

@RestController
@RequestMapping("/api/toolkit")
@CrossOrigin("*")
public class ToolkitController {
	
	@Autowired
	ToolkitServices toolkitservice;
	
	@PostMapping("/")
	public ResponseEntity<?> addToolkit(@RequestBody Toolkit t){
		return toolkitservice.addToolkit(t);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllToolkits(){
		return toolkitservice.getAllToolkit();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getToolkitById(@PathVariable Integer id){
		return toolkitservice.getAllToolkitById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateToolkit(@RequestBody Toolkit t,@PathVariable Integer id){
		return toolkitservice.updateToolkit(t, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteToolkit(@PathVariable Integer id){
		return toolkitservice.deleteToolKit(id);
	}

}

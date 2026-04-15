package com.example.demo.Entities;

import com.example.demo.enums.UserRole;

import jakarta.persistence.Entity;

@Entity
public class LabAssistant extends User {

    private String labName;     // 🔥 which lab
    private String shift;       // morning/evening
    private String employeeId;  // optional
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public LabAssistant(Integer id, String userName, String password, UserRole role, String email, String phone,
			String labName, String shift, String employeeId) {
		super(id, userName, password, role, email, phone);
		this.labName = labName;
		this.shift = shift;
		this.employeeId = employeeId;
	}
	public LabAssistant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LabAssistant(Integer id, String userName, String password, UserRole role, String email, String phone) {
		super(id, userName, password, role, email, phone);
		// TODO Auto-generated constructor stub
	}

    // getters & setters
    
}

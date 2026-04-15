package com.example.demo.Entities;

import com.example.demo.enums.UserRole;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {

    private String department;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Admin(Integer id, String userName, String password, UserRole role, String email, String phone,
			String department) {
		super(id, userName, password, role, email, phone);
		this.department = department;
	}

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(Integer id, String userName, String password, UserRole role, String email, String phone) {
		super(id, userName, password, role, email, phone);
		// TODO Auto-generated constructor stub
	}

   
    
}

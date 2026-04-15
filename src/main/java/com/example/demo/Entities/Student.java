package com.example.demo.Entities;

import com.example.demo.enums.UserRole;

import jakarta.persistence.Entity;

@Entity
public class Student extends User {

    private String branch;
    private String division;   // 🔥 NEW
    private String prn;        // 🔥 UNIQUE STUDENT ID
    private int year;
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPrn() {
		return prn;
	}
	public void setPrn(String prn) {
		this.prn = prn;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Student(Integer id, String userName, String password, UserRole role, String email, String phone,
			String branch, String division, String prn, int year) {
		super(id, userName, password, role, email, phone);
		this.branch = branch;
		this.division = division;
		this.prn = prn;
		this.year = year;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(Integer id, String userName, String password, UserRole role, String email, String phone) {
		super(id, userName, password, role, email, phone);
		// TODO Auto-generated constructor stub
	}

    
}
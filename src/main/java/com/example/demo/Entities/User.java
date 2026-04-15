package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import com.example.demo.enums.UserRole;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String userName;
	String password;
	
	@Enumerated(EnumType.STRING)
	UserRole role;
	String email;
	String phone;
	
	
	

	


	public Integer getId() {
		return id;
	}







	public void setId(Integer id) {
		this.id = id;
	}







	public String getUserName() {
		return userName;
	}







	public void setUserName(String userName) {
		this.userName = userName;
	}







	public String getPassword() {
		return password;
	}







	public void setPassword(String password) {
		this.password = password;
	}







	public UserRole getRole() {
		return role;
	}







	public void setRole(UserRole role) {
		this.role = role;
	}







	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public String getPhone() {
		return phone;
	}







	public void setPhone(String phone) {
		this.phone = phone;
	}







	public User(Integer id, String userName, String password, UserRole role, String email, String phone) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.email = email;
		this.phone = phone;
	}







	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	

}

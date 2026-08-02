package com.example.demo.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.User;
import com.example.demo.enums.UserRole;

@Repository
public interface userRepo extends JpaRepository<User, Integer> {
	public User findByUserName(String name);
	public User findByEmail(String email);
	public List<User> findByRole(UserRole role);
}

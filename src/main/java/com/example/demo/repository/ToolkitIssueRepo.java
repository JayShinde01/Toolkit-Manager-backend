package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.ToolkitIssue;

public interface ToolkitIssueRepo extends JpaRepository<ToolkitIssue, Integer>{
	List<ToolkitIssue> findByUserId(Integer userId);
}

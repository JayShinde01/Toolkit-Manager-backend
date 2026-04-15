package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Entities.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
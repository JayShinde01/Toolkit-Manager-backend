package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entities.Toolkit;

public interface ToolkitRepo extends JpaRepository<Toolkit,Integer>{
	Optional<Toolkit> findByQrCode(String qrCode);
}

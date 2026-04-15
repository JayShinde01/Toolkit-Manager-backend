// File: src/main/java/com/example/demo/security/JwtUtil.java
package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    // Generates a secure secret key for the HS256 algorithm
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Token is valid for 10 hours (measured in milliseconds)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; 

    // --- 1. Generates the token when a user logs in ---
    public String generateToken(String userName, String role) {
    	System.out.println("in utils file");
        return Jwts.builder()
                .setSubject(userName)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // --- 2. Gets the key so your JwtFilter can read incoming tokens ---
    public Key getKey() {
        return this.key;
    }
}
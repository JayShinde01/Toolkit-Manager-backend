package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
            .authorizeHttpRequests(auth -> auth
                // Allow these endpoints without a token
                .requestMatchers("/register", "/login","/api/toolkit/*").permitAll() 
                .requestMatchers("/api/courses/create").hasRole("TEACHER")
                // Any other endpoint requires a valid token
                .anyRequest().permitAll() 
            )
            // APIs shouldn't keep sessions, rely strictly on the token
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Insert our custom JWT filter before standard Spring Security filters
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        System.out.println("in config file");
        return http.build();
    }

    // Since we are starting simple, this tells Spring not to encode passwords yet.
    // We can upgrade this to BCrypt later!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); 
    }
}
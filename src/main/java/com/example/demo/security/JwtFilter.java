package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        Claims claims = null; // We need this to get the role

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                // 1. Parse the token and get the claims
                claims = Jwts.parserBuilder().setSigningKey(jwtUtil.getKey()).build().parseClaimsJws(token).getBody();
                userName = claims.getSubject();
            } catch (Exception e) {
                System.out.println("Invalid JWT Token");
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 2. Extract the role string from the claims
            String role = claims.get("role", String.class);
            
            if (role != null) {
                // 3. Create the authority. 
                // IMPORTANT: Spring Security looks for "ROLE_" prefix when using hasRole()
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                
                // 4. Pass the authority list instead of a new ArrayList<>()
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userName, null, Collections.singletonList(authority));
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authenticated user: " + userName + " with role: " + role);
            }
        }

        filterChain.doFilter(request, response);
    }
}
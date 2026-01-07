package com.product.config.jwt;

import java.util.HashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import org.springframework.security.core.userdetails.User;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        java.util.Map<String, Object> claimsMap = jwtUtil.extractClaims(token);
        
        String username = (String) claimsMap.get("email");
        java.util.List<java.util.HashMap<String, String>> permisos = jwtUtil.extractPermisos(token);
        
        java.util.List<String> permisosList = permisos.stream().map(i -> i.get("authority")).toList();
        

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails authoritiesDetails = User.withUsername(username)
                .password("")
                .authorities(permisosList.toArray(new String[0]))
                .build();
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    claimsMap,
                    null, 
                    authoritiesDetails.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        chain.doFilter(request, response);
    }
}

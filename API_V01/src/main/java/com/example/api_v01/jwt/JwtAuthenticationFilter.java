package com.example.api_v01.jwt;

import com.example.api_v01.service.user_service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        System.out.println("Authorization header: " + authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
            try {
                username = jwtUtils.extractUsername(jwtToken);
            }catch (Exception e){
                System.out.println("Error extrayendo username del token: " + e.getMessage());
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(username);

            if(jwtUtils.validateToken(jwtToken, userDetails)){
                //Extrae rol del token
                String role = jwtUtils.extractAllClaims(jwtToken).get("role", String.class);
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, List.of(authority));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

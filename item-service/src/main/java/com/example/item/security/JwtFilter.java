package com.example.item.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            boolean valid = jwtUtil.validate(token);
            System.out.println("Token valid: " + valid);

            if (valid) {

                Claims claims = jwtUtil.parse(token);

                String userId = claims.getSubject();
                String role = (String) claims.get("role");


                request.setAttribute("userId", Long.valueOf(userId));
                request.setAttribute("role", role);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                "user", // principal
                                null,
                                Collections.emptyList()
                        );


                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

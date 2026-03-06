package com.example.paymentservice.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET = "shopping-shopping-shopping-shopping-shopping-shopping-shopping";

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
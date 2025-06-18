package com.example.seniya_back.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtProvider {
    private final Key key;
    private final int jwtExpirationMs;
    private final long jwtEmailExpirationMs;

    public int getExpiration() {
        return jwtExpirationMs;
    }

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration}") int jwtExpirationMs,
                       @Value("${jwt.email-expiration-ms}") long jwtEmailExpirationMs) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtEmailExpirationMs = jwtEmailExpirationMs;
    }

    public String generateToken(String username, String role, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null when generating token");
        }

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid JWT token format");
        }
        return bearerToken.substring("Bearer ".length());
    }

    public boolean isValidToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            // 예외를 로깅하거나 원하는 처리 가능
            return false;
        }
    }

    public Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = getClaims(token);
        return claims.get("username", String.class);
    }

    public String getRoleFromJwt(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }


    public Optional<Long> getUserIdFromJwt(String token) {
        Claims claims = getClaims(token);
        Object userIdObj = claims.get("userId");
        if (userIdObj == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(Long.valueOf(userIdObj.toString()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

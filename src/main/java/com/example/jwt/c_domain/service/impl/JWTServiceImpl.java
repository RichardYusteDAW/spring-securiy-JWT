package com.example.jwt.c_domain.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.jwt.c_domain.service.interfaces.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access.expiration}")
    private Long jwtAccessExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long jwtRefreshExpiration;

    @Override
    public String generateAccessToken(String email) {
        return buildToken(email, jwtAccessExpiration);
    }

    @Override
    public String generateRefreshToken(String email) {
        return buildToken(email, jwtRefreshExpiration);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public Date getExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    /***** PRIVATE METHODS *****/
    private String buildToken(String email, Long expiration) {
        String token = Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();

        return token;
    }

    private SecretKey getSignInKey() {
        final byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
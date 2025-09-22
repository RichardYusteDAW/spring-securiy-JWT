package com.example.jwt.c_domain.service.interfaces;

import java.util.Date;

public interface JWTService {

    String generateAccessToken(String userId);

    String generateRefreshToken(String userId);

    boolean validateToken(String token);

    String getSubject(String token);

    Date getExpiration(String token);
}
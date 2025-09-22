package com.example.jwt.b_presentation.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.b_presentation.models.CheckinRequest;
import com.example.jwt.b_presentation.models.LoginRequest;
import com.example.jwt.b_presentation.models.TokenResponse;
import com.example.jwt.c_domain.models.User;
import com.example.jwt.c_domain.service.interfaces.JWTService;
import com.example.jwt.c_domain.service.interfaces.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody CheckinRequest checkinRequest) {

        User user = userService.create(checkinRequest);

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest);

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }

        String refreshToken = authorization.substring(7);
        if (!jwtService.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String userId = jwtService.getSubject(refreshToken);

        String newRefreshToken = jwtService.generateRefreshToken(userId);
        String accessToken = jwtService.generateAccessToken(userId);
        TokenResponse tokenResponse = new TokenResponse(accessToken, newRefreshToken);

        return ResponseEntity.ok(tokenResponse);
    }
}
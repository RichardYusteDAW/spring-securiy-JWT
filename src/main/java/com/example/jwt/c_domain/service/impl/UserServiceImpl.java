package com.example.jwt.c_domain.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.b_presentation.models.CheckinRequest;
import com.example.jwt.b_presentation.models.LoginRequest;
import com.example.jwt.c_domain.models.User;
import com.example.jwt.c_domain.repository.UserRepository;
import com.example.jwt.c_domain.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id.toString())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public User create(CheckinRequest checkinRequest) {
        userRepository.findByEmail(checkinRequest.email())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("User with email already exists");
                });

        User user = new User(
                UUID.randomUUID(),
                checkinRequest.name(),
                checkinRequest.email(),
                passwordEncoder.encode(checkinRequest.password()));

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        this.findById(user.getId());

        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        this.findById(id);

        userRepository.delete(id.toString());
    }

    @Override
    public User login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.password());
        authenticationManager.authenticate(authenticationToken);

        return userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
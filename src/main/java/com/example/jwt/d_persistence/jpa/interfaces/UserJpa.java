package com.example.jwt.d_persistence.jpa.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.d_persistence.jpa.models.UserEntity;

public interface UserJpa extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
package com.example.jwt.c_domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.jwt.c_domain.models.User;;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void delete(String id);
}
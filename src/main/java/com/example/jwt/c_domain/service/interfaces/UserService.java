package com.example.jwt.c_domain.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.jwt.b_presentation.models.CheckinRequest;
import com.example.jwt.b_presentation.models.LoginRequest;
import com.example.jwt.c_domain.models.User;

public interface UserService {

    List<User> findAll();

    User findById(UUID id);

    User create(CheckinRequest checkinRequest);

    User update(User user);

    void delete(UUID id);

    User login(LoginRequest loginRequest);
}
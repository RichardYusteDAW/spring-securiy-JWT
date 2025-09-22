package com.example.jwt.b_presentation.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
                @NotBlank @Email String email,
                @NotBlank @Size(min = 8, max = 100) String password) {
}

package com.example.polytech.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public record User(
        UUID id,
        @Email @NotBlank String email,
        @NotBlank String fullName,
        Instant createdAt
) {
    public static User newUser(String email, String fullName) {
        return new User(UUID.randomUUID(), email, fullName, Instant.now());
    }
}

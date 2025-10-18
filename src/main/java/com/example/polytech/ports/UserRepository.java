package com.example.polytech.ports;

import com.example.polytech.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User u);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
}

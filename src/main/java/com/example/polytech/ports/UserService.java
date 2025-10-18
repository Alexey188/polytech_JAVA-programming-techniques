package com.example.polytech.ports;

import com.example.polytech.domain.User;

import java.util.Optional;

public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public User register(String email, String fullName) {
        if (repo.existsByEmail(email)) throw new DuplicateEmail();
        return repo.save(User.newUser(email, fullName));
    }

    public Optional<User> loginByEmail(String email) {
        return repo.findByEmail(email);
    }

    public static class DuplicateEmail extends RuntimeException {}
}

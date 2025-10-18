package com.example.polytech.adapters.storage.inmem;

import com.example.polytech.domain.User;
import com.example.polytech.ports.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("inmem")
public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> byId = new ConcurrentHashMap<>();
    private final Map<String, UUID> emailToId = new ConcurrentHashMap<>();

    @Override public User save(User u) {
        byId.put(u.id(), u);
        emailToId.put(u.email().toLowerCase(), u.id());
        return u;
    }

    @Override public Optional<User> findByEmail(String email) {
        UUID id = emailToId.get(email.toLowerCase());
        return id == null ? Optional.empty() : Optional.ofNullable(byId.get(id));
    }

    @Override public Optional<User> findById(UUID id) { return Optional.ofNullable(byId.get(id)); }

    @Override public boolean existsByEmail(String email) { return emailToId.containsKey(email.toLowerCase()); }
}

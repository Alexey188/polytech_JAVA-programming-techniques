package com.example.polytech.adapters.storage.jpa.impl;

import com.example.polytech.adapters.storage.jpa.model.UserEntity;
import com.example.polytech.adapters.storage.jpa.repo.UserJpaRepository;
import com.example.polytech.domain.User;
import com.example.polytech.ports.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository @Profile("h2")
public class UserRepositoryH2 implements UserRepository {
    private final UserJpaRepository jpa;
    public UserRepositoryH2(UserJpaRepository jpa){ this.jpa=jpa; }

    private static User toDomain(UserEntity e){ return new User(e.getId(), e.getEmail(), e.getFullName(), e.getCreatedAt()); }
    private static UserEntity toEntity(User d){ return new UserEntity(d.id(), d.email(), d.fullName(), d.createdAt()); }

    @Override public User save(User u){ return toDomain(jpa.save(toEntity(u))); }
    @Override public java.util.Optional<User> findByEmail(String email){ return jpa.findByEmailIgnoreCase(email).map(UserRepositoryH2::toDomain); }
    @Override public java.util.Optional<User> findById(UUID id){ return jpa.findById(id).map(UserRepositoryH2::toDomain); }
    @Override public boolean existsByEmail(String email){ return jpa.existsByEmailIgnoreCase(email); }
}

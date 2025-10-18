package com.example.polytech.adapters.storage.jpa.repo;

import com.example.polytech.adapters.storage.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserJpaRepository extends JpaRepository<UserEntity, java.util.UUID> {
    Optional<UserEntity> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}

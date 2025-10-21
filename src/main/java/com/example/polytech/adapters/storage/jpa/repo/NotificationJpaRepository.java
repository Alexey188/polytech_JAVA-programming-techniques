package com.example.polytech.adapters.storage.jpa.repo;

import com.example.polytech.adapters.storage.jpa.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, java.util.UUID> {
    List<NotificationEntity> findByUserIdOrderByCreatedAtAsc(UUID userId);
    List<NotificationEntity> findByUserIdAndReadFalseOrderByCreatedAtAsc(UUID userId);
}

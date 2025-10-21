package com.example.polytech.adapters.storage.jpa.repo;

import com.example.polytech.adapters.storage.jpa.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, java.util.UUID> {
    List<TaskEntity> findByUserIdAndDeletedFalseOrderByCreatedAtAsc(UUID userId);
    List<TaskEntity> findByUserIdAndStatusAndDeletedFalseOrderByCreatedAtAsc(UUID userId, String status);
    List<TaskEntity> findByTargetDateBeforeAndStatusAndDeletedFalse(java.time.Instant targetDate, String status);
}

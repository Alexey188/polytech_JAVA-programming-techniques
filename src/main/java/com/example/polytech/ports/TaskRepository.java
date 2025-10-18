package com.example.polytech.ports;

import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;

import java.util.*;

public interface TaskRepository {
    Task save(Task t);
    Optional<Task> findById(UUID id);
    List<Task> findByUser(UUID userId, boolean includeDeleted);
    List<Task> findByUserAndStatus(UUID userId, TaskStatus status, boolean includeDeleted);
    Optional<Task> softDelete(UUID id); // set deleted=true
}

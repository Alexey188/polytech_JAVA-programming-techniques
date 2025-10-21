package com.example.polytech.adapters.storage.inmem;

import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;
import com.example.polytech.ports.TaskRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("inmem")
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<UUID, Task> data = new ConcurrentHashMap<>();

    @Override public Task save(Task t) { data.put(t.id(), t); return t; }

    @Override public Optional<Task> findById(UUID id) { return Optional.ofNullable(data.get(id)); }

    @Override public List<Task> findByUser(UUID userId, boolean includeDeleted) {
        return data.values().stream()
                .filter(t -> t.userId().equals(userId))
                .filter(t -> includeDeleted || !t.deleted())
                .sorted(Comparator.comparing(Task::createdAt))
                .toList();
    }

    @Override public List<Task> findByUserAndStatus(UUID userId, TaskStatus status, boolean includeDeleted) {
        return data.values().stream()
                .filter(t -> t.userId().equals(userId))
                .filter(t -> t.status() == status)
                .filter(t -> includeDeleted || !t.deleted())
                .sorted(Comparator.comparing(Task::createdAt))
                .toList();
    }

    @Override public Optional<Task> softDelete(UUID id) {
        return Optional.ofNullable(data.computeIfPresent(id, (k, v) -> v.markDeleted()));
    }

    @Override public List<Task> findOverdueTasks(java.time.Instant now) {
        return data.values().stream()
                .filter(t -> !t.deleted())
                .filter(t -> t.status() == TaskStatus.PENDING)
                .filter(t -> t.targetDate() != null && t.targetDate().isBefore(now))
                .sorted(Comparator.comparing(Task::targetDate))
                .toList();
    }
}

package com.example.polytech.domain;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

public record Task(
        UUID id,
        UUID userId,
        @NotBlank String title,
        String description,
        TaskStatus status,
        boolean deleted,
        Instant createdAt,
        Instant targetDate
) {
    public static Task newTask(UUID userId, String title, String description, Instant targetDate) {
        return new Task(UUID.randomUUID(), userId, title, description, TaskStatus.PENDING, false, Instant.now(), targetDate);
    }
    public Task markDeleted() { return new Task(id, userId, title, description, status, true, createdAt, targetDate); }
    public Task withStatus(TaskStatus s) { return new Task(id, userId, title, description, s, deleted, createdAt, targetDate); }
}

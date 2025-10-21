package com.example.polytech.domain;

import java.time.Instant;
import java.util.UUID;

public record TaskCreatedEvent(
        UUID taskId,
        UUID userId,
        String title,
        String description,
        Instant createdAt
) {
    public static TaskCreatedEvent from(Task task) {
        return new TaskCreatedEvent(
                task.id(),
                task.userId(),
                task.title(),
                task.description(),
                task.createdAt()
        );
    }
}

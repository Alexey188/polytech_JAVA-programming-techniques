package com.example.polytech.domain;

import java.time.Instant;
import java.util.UUID;

public record Notification(
        UUID id,
        UUID userId,
        String message,
        boolean read,
        Instant createdAt
) {
    public static Notification newNotification(UUID userId, String message) {
        return new Notification(UUID.randomUUID(), userId, message, false, Instant.now());
    }

    public Notification markRead() {
        return new Notification(id, userId, message, true, createdAt);
    }
}

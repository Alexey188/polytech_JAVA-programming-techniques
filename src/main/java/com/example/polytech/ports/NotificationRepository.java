package com.example.polytech.ports;

import com.example.polytech.domain.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {
    Notification save(Notification n);
    List<Notification> findByUser(UUID userId, boolean includeRead);
    Optional<Notification> findById(UUID id);
    void markAsRead(UUID id);
}

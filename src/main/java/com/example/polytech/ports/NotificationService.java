package com.example.polytech.ports;

import com.example.polytech.domain.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    private final NotificationRepository repo;
    public NotificationService(NotificationRepository repo) { this.repo = repo; }

    public Notification create(Notification n) { return repo.save(n); }

    public List<Notification> getAll(UUID userId) {
        return repo.findByUser(userId, true);
    }

    public List<Notification> getPending(UUID userId) {
        return repo.findByUser(userId, false);
    }

    public void markRead(UUID id) { repo.markAsRead(id); }
}

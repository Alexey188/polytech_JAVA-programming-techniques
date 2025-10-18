package com.example.polytech.adapters.storage.inmem;

import com.example.polytech.domain.Notification;
import com.example.polytech.ports.NotificationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("inmem")
public class InMemoryNotificationRepository implements NotificationRepository {

    private final Map<UUID, Notification> data = new ConcurrentHashMap<>();

    @Override public Notification save(Notification n) {
        data.put(n.id(), n);
        return n;
    }

    @Override public List<Notification> findByUser(UUID userId, boolean includeRead) {
        return data.values().stream()
                .filter(n -> n.userId().equals(userId))
                .filter(n -> includeRead || !n.read())
                .sorted(Comparator.comparing(Notification::createdAt))
                .toList();
    }

    @Override public Optional<Notification> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override public void markAsRead(UUID id) {
        data.computeIfPresent(id, (k, v) -> v.markRead());
    }
}

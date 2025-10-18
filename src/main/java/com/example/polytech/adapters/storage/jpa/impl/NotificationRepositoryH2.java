package com.example.polytech.adapters.storage.jpa.impl;

import com.example.polytech.adapters.storage.jpa.model.NotificationEntity;
import com.example.polytech.adapters.storage.jpa.repo.NotificationJpaRepository;
import com.example.polytech.domain.Notification;
import com.example.polytech.ports.NotificationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import static java.util.stream.Collectors.toList;

@Repository @Profile("h2")
public class NotificationRepositoryH2 implements NotificationRepository {
    private final NotificationJpaRepository jpa;
    public NotificationRepositoryH2(NotificationJpaRepository jpa){ this.jpa=jpa; }

    private static Notification toDomain(NotificationEntity e){
        return new Notification(e.getId(), e.getUserId(), e.getMessage(), e.isRead(), e.getCreatedAt());
    }
    private static NotificationEntity toEntity(Notification d){
        return new NotificationEntity(d.id(), d.userId(), d.message(), d.read(), d.createdAt());
    }

    @Override public Notification save(Notification n){ return toDomain(jpa.save(toEntity(n))); }

    @Override public List<Notification> findByUser(UUID userId, boolean includeRead){
        var list = includeRead
                ? jpa.findByUserIdOrderByCreatedAtAsc(userId)
                : jpa.findByUserIdAndReadFalseOrderByCreatedAtAsc(userId);
        return list.stream().map(NotificationRepositoryH2::toDomain).collect(toList());
    }

    @Override public Optional<Notification> findById(UUID id){ return jpa.findById(id).map(NotificationRepositoryH2::toDomain); }

    @Override public void markAsRead(UUID id){
        jpa.findById(id).ifPresent(e -> { e.setRead(true); jpa.save(e); });
    }
}

package com.example.polytech.adapters.storage.jpa.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="notifications", indexes = {
        @Index(name="idx_notif_user", columnList="userId,readFlag")
})
public class NotificationEntity {
    @Id private UUID id;
    @Column(nullable=false) private UUID userId;
    @Column(nullable=false) private String message;
    @Column(name="readFlag", nullable=false) private boolean read;
    @Column(nullable=false) private Instant createdAt;

    protected NotificationEntity() {}
    public NotificationEntity(UUID id, UUID userId, String message, boolean read, Instant createdAt){
        this.id=id; this.userId=userId; this.message=message; this.read=read; this.createdAt=createdAt;
    }
    public UUID getId(){return id;} public UUID getUserId(){return userId;}
    public String getMessage(){return message;} public boolean isRead(){return read;}
    public Instant getCreatedAt(){return createdAt;} public void setRead(boolean r){this.read=r;}
}

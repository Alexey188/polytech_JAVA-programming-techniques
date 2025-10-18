package com.example.polytech.adapters.storage.jpa.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="tasks", indexes = {
        @Index(name="idx_tasks_user", columnList="userId,deleted,status")
})
public class TaskEntity {
    @Id private UUID id;
    @Column(nullable=false) private UUID userId;
    @Column(nullable=false) private String title;
    @Column private String description;
    @Column(nullable=false) private String status;   // PENDING/DONE
    @Column(nullable=false) private boolean deleted;
    @Column(nullable=false) private Instant createdAt;
    @Column private Instant targetDate;

    protected TaskEntity() {}
    public TaskEntity(UUID id, UUID userId, String title, String description,
                      String status, boolean deleted, Instant createdAt, Instant targetDate){
        this.id=id; this.userId=userId; this.title=title; this.description=description;
        this.status=status; this.deleted=deleted; this.createdAt=createdAt; this.targetDate=targetDate;
    }
    public UUID getId(){return id;} public UUID getUserId(){return userId;}
    public String getTitle(){return title;} public String getDescription(){return description;}
    public String getStatus(){return status;} public boolean isDeleted(){return deleted;}
    public Instant getCreatedAt(){return createdAt;} public Instant getTargetDate(){return targetDate;}
    public void setDeleted(boolean v){this.deleted=v;} public void setStatus(String s){this.status=s;}
}

package com.example.polytech.adapters.storage.jpa.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="users")
public class UserEntity {
    @Id private UUID id;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String fullName;
    @Column(nullable=false) private Instant createdAt;

    protected UserEntity() {}
    public UserEntity(UUID id, String email, String fullName, Instant createdAt){
        this.id=id; this.email=email; this.fullName=fullName; this.createdAt=createdAt;
    }
    public UUID getId(){return id;} public String getEmail(){return email;}
    public String getFullName(){return fullName;} public Instant getCreatedAt(){return createdAt;}
}

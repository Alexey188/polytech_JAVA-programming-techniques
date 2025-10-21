package com.example.polytech.adapters.storage.jpa.impl;

import com.example.polytech.adapters.storage.jpa.model.TaskEntity;
import com.example.polytech.adapters.storage.jpa.repo.TaskJpaRepository;
import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;
import com.example.polytech.ports.TaskRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import static java.util.stream.Collectors.toList;

@Repository @Profile({"h2","postgres"})
public class TaskRepositoryH2 implements TaskRepository {
    private final TaskJpaRepository jpa;
    public TaskRepositoryH2(TaskJpaRepository jpa){ this.jpa=jpa; }

    private static Task toDomain(TaskEntity e){
        return new Task(e.getId(), e.getUserId(), e.getTitle(), e.getDescription(),
                TaskStatus.valueOf(e.getStatus()), e.isDeleted(), e.getCreatedAt(), e.getTargetDate());
    }
    private static TaskEntity toEntity(Task d){
        return new TaskEntity(d.id(), d.userId(), d.title(), d.description(),
                d.status().name(), d.deleted(), d.createdAt(), d.targetDate());
    }

    @Override public Task save(Task t){ return toDomain(jpa.save(toEntity(t))); }
    @Override public Optional<Task> findById(UUID id){ return jpa.findById(id).map(TaskRepositoryH2::toDomain); }

    @Override public List<Task> findByUser(UUID userId, boolean includeDeleted){
        List<TaskEntity> list = includeDeleted
                ? jpa.findAll().stream().filter(e->e.getUserId().equals(userId)).collect(toList())
                : jpa.findByUserIdAndDeletedFalseOrderByCreatedAtAsc(userId);
        return list.stream().map(TaskRepositoryH2::toDomain).collect(toList());
    }

    @Override public List<Task> findByUserAndStatus(UUID userId, com.example.polytech.domain.TaskStatus status, boolean includeDeleted){
        List<TaskEntity> list = includeDeleted
                ? jpa.findAll().stream().filter(e->e.getUserId().equals(userId) && e.getStatus().equals(status.name())).collect(toList())
                : jpa.findByUserIdAndStatusAndDeletedFalseOrderByCreatedAtAsc(userId, status.name());
        return list.stream().map(TaskRepositoryH2::toDomain).collect(toList());
    }

    @Override public Optional<Task> softDelete(UUID id){
        return jpa.findById(id).map(e -> { e.setDeleted(true); return toDomain(jpa.save(e)); });
    }

    @Override public List<Task> findOverdueTasks(java.time.Instant now){
        return jpa.findByTargetDateBeforeAndStatusAndDeletedFalse(now, TaskStatus.PENDING.name())
                .stream().map(TaskRepositoryH2::toDomain).collect(toList());
    }
}

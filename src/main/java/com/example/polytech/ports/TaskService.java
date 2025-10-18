// src/main/java/com/example/polytech/ports/TaskService.java
package com.example.polytech.ports;

import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repo;
    public TaskService(TaskRepository repo) { this.repo = repo; }

    @Caching(evict = {
            @CacheEvict(cacheNames = "taskListByUser", allEntries = true),
            @CacheEvict(cacheNames = "taskListByUserPending", allEntries = true)
    })
    public Task create(Task t) {
        return repo.save(t);
    }

    @Cacheable(
            cacheNames = "taskListByUser",
            key = "#userId.toString()",
            unless = "#result == null || #result.isEmpty()"
    )
    public List<Task> listUserTasks(UUID userId) {
        log.info("CACHE MISS - DB HIT: listUserTasks for userId={}", userId);
        return repo.findByUser(userId, false);
    }

    @Cacheable(
            cacheNames = "taskListByUserPending",
            key = "#userId.toString()",
            unless = "#result == null || #result.isEmpty()"
    )
    public List<Task> listUserPending(UUID userId) {
        log.info("CACHE MISS - DB HIT: listUserPending for userId={}", userId);
        return repo.findByUserAndStatus(userId, TaskStatus.PENDING, false);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "taskListByUser", allEntries = true),
            @CacheEvict(cacheNames = "taskListByUserPending", allEntries = true)
    })
    public void softDelete(UUID taskId) {
        if (repo.softDelete(taskId).isEmpty()) throw new NotFound();
    }

    public static class NotFound extends RuntimeException {}
}

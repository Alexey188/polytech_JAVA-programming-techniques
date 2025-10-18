package com.example.polytech.ports;

import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;

import java.util.*;

public class TaskService {
    private final TaskRepository repo;
    public TaskService(TaskRepository repo) { this.repo = repo; }

    public Task create(Task t) { return repo.save(t); }
    public List<Task> listUserTasks(UUID userId) { return repo.findByUser(userId, false); }
    public List<Task> listUserPending(UUID userId) { return repo.findByUserAndStatus(userId, TaskStatus.PENDING, false); }
    public void softDelete(UUID taskId) {
        if (repo.softDelete(taskId).isEmpty()) throw new NotFound();
    }

    public static class NotFound extends RuntimeException {}
}

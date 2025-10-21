package com.example.polytech.adapters.web;
import java.time.Instant;
import com.example.polytech.domain.Task;
import com.example.polytech.ports.TaskRepository;
import com.example.polytech.ports.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    public record CreateTaskRequest(@NotBlank String title, String description, Instant targetDate) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@PathVariable UUID userId, @Valid @RequestBody CreateTaskRequest body) {
        return service.create(Task.newTask(userId, body.title(), body.description(), body.targetDate()));
    }

    @GetMapping
    public java.util.List<Task> all(@PathVariable UUID userId) {
        return service.listUserTasks(userId);
    }

    @GetMapping("/pending")
    public java.util.List<Task> pending(@PathVariable UUID userId) {
        return service.listUserPending(userId);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable UUID taskId) {
        service.softDelete(taskId);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskService.NotFound.class)
    public void notFound() { /* 404 */ }
}

package com.example.polytech.adapters.web;

import com.example.polytech.domain.Notification;
import com.example.polytech.ports.NotificationRepository;
import com.example.polytech.ports.NotificationService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/notifications")
public class NotificationController {

    private final NotificationService service;
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Notification> all(@PathVariable UUID userId) {
        return service.getAll(userId);
    }

    @GetMapping("/pending")
    public List<Notification> pending(@PathVariable UUID userId) {
        return service.getPending(userId);
    }

    @PutMapping("/{id}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markRead(@PathVariable UUID id) {
        service.markRead(id);
    }
}

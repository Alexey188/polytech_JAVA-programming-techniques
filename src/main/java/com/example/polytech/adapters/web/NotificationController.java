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
    public NotificationController(NotificationRepository repo) {
        this.service = new NotificationService(repo);
    }

    // DTO для создания уведомлений
    public record CreateNotificationRequest(@NotBlank String message) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notification create(@PathVariable UUID userId,
                               @RequestBody CreateNotificationRequest body) {
        return service.create(Notification.newNotification(userId, body.message()));
    }

    // GET — все уведомления пользователя
    @GetMapping
    public List<Notification> all(@PathVariable UUID userId) {
        return service.getAll(userId);
    }

    // GET — только непрочитанные уведомления
    @GetMapping("/pending")
    public List<Notification> pending(@PathVariable UUID userId) {
        return service.getPending(userId);
    }

    // PUT — пометить как прочитанное
    @PutMapping("/{id}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markRead(@PathVariable UUID id) {
        service.markRead(id);
    }
}

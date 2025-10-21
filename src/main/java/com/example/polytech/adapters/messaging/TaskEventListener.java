package com.example.polytech.adapters.messaging;

import com.example.polytech.domain.Notification;
import com.example.polytech.domain.TaskCreatedEvent;
import com.example.polytech.ports.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {
    private static final Logger log = LoggerFactory.getLogger(TaskEventListener.class);

    private final NotificationService notificationService;

    public TaskEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "task-created-events", groupId = "polytech-notifications")
    public void handleTaskCreated(TaskCreatedEvent event) {
        log.info("Received task created event: taskId={}, userId={}", event.taskId(), event.userId());
        
        String message = String.format("New task created: '%s'", event.title());
        Notification notification = Notification.newNotification(event.userId(), message);
        
        notificationService.create(notification);
        log.info("Notification created for user: {}", event.userId());
    }
}

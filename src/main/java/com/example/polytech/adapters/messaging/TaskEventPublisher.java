package com.example.polytech.adapters.messaging;

import com.example.polytech.domain.TaskCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(TaskEventPublisher.class);
    private static final String TOPIC = "task-created-events";

    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;

    public TaskEventPublisher(KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTaskCreated(TaskCreatedEvent event) {
        log.info("Publishing task created event: taskId={}, userId={}", event.taskId(), event.userId());
        kafkaTemplate.send(TOPIC, event.userId().toString(), event);
    }
}

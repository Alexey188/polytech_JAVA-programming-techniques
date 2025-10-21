package com.example.polytech.ports;

import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ScheduledTaskService {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTaskService.class);

    private final TaskRepository taskRepo;

    public ScheduledTaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Scheduled(fixedRate = 60000)
    public void checkOverdueTasks() {
        log.info("Checking for overdue tasks...");
        List<Task> overdue = taskRepo.findOverdueTasks(Instant.now());
        if (!overdue.isEmpty()) {
            log.info("Found {} overdue tasks", overdue.size());
            processOverdueTasksAsync(overdue);
        }
    }

    @Async
    public void processOverdueTasksAsync(List<Task> tasks) {
        log.info("Processing {} overdue tasks asynchronously", tasks.size());
        tasks.forEach(task -> {
            Task updated = task.withStatus(TaskStatus.OVERDUE);
            taskRepo.save(updated);
            log.info("Task {} marked as OVERDUE", task.id());
        });
    }
}

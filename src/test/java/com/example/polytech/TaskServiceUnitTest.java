package com.example.polytech;

import com.example.polytech.adapters.messaging.TaskEventPublisher;
import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskCreatedEvent;
import com.example.polytech.ports.TaskRepository;
import com.example.polytech.ports.TaskService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceUnitTest {
    static class MemRepo implements TaskRepository {
        final Map<UUID, Task> m = new HashMap<>();
        public Task save(Task t){ m.put(t.id(), t); return t; }
        public Optional<Task> findById(UUID id){ return Optional.ofNullable(m.get(id)); }
        public List<Task> findByUser(UUID u, boolean inc){ return m.values().stream().filter(t->t.userId().equals(u)&& (inc || !t.deleted())).toList(); }
        public List<Task> findByUserAndStatus(UUID u, com.example.polytech.domain.TaskStatus s, boolean inc){
            return m.values().stream().filter(t->t.userId().equals(u)&& t.status()==s && (inc || !t.deleted())).toList();
        }
        public Optional<Task> softDelete(UUID id){ return Optional.ofNullable(m.computeIfPresent(id,(k,v)->v.markDeleted())); }
    }

    static class NoOpEventPublisher extends TaskEventPublisher {
        public NoOpEventPublisher() {
            super(null);
        }
        @Override
        public void publishTaskCreated(TaskCreatedEvent event) {
        }
    }

    @Test
    void create_and_softDelete() {
        var repo = new MemRepo();
        var eventPublisher = new NoOpEventPublisher();
        var svc = new TaskService(repo, eventPublisher);
        UUID user = UUID.randomUUID();
        Task t = svc.create(Task.newTask(user, "A","d", Instant.parse("2030-01-01T00:00:00Z")));
        assertEquals(1, svc.listUserTasks(user).size());
        svc.softDelete(t.id());
        assertEquals(0, svc.listUserTasks(user).size());
    }
}

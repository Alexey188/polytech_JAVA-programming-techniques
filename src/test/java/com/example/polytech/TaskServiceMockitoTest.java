package com.example.polytech;

import com.example.polytech.domain.Task;
import com.example.polytech.domain.TaskStatus;
import com.example.polytech.ports.TaskRepository;
import com.example.polytech.ports.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceMockitoTest {

    @Mock TaskRepository repo;
    @InjectMocks TaskService service;

    @Test
    void create_and_list() {
        UUID user = UUID.randomUUID();
        Task t = Task.newTask(user, "Read", "ch1", Instant.parse("2030-01-01T00:00:00Z"));

        when(repo.save(any())).thenReturn(t);
        when(repo.findByUser(eq(user), eq(false))).thenReturn(List.of(t));

        Task saved = service.create(t);
        assertThat(saved.title()).isEqualTo("Read");

        List<Task> list = service.listUserTasks(user);
        assertThat(list).hasSize(1);

        ArgumentCaptor<Task> cap = ArgumentCaptor.forClass(Task.class);
        verify(repo).save(cap.capture());
        assertThat(cap.getValue().userId()).isEqualTo(user);
    }

    @Test
    void soft_delete() {
        UUID id = UUID.randomUUID();
        when(repo.softDelete(id)).thenReturn(Optional.of(mock(Task.class)));

        service.softDelete(id);
        verify(repo).softDelete(id);
    }
}

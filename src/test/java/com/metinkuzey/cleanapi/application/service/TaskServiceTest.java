package com.metinkuzey.cleanapi.application.service;

import com.metinkuzey.cleanapi.application.port.in.CreateTaskCommand;
import com.metinkuzey.cleanapi.application.port.out.TaskRepository;
import com.metinkuzey.cleanapi.domain.exception.DuplicateTaskException;
import com.metinkuzey.cleanapi.domain.exception.TaskNotFoundException;
import com.metinkuzey.cleanapi.domain.model.TaskItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository);
    }

    @Test
    void shouldCreateTaskWhenTitleIsUnique() {
        when(taskRepository.findByTitleIgnoreCase("Prepare sprint review")).thenReturn(Optional.empty());
        when(taskRepository.save(any(TaskItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskItem created = taskService.createTask(new CreateTaskCommand("Prepare sprint review", "Gather metrics"));

        assertThat(created.getTitle()).isEqualTo("Prepare sprint review");
        assertThat(created.getDescription()).isEqualTo("Gather metrics");
        verify(taskRepository).save(any(TaskItem.class));
    }

    @Test
    void shouldRejectDuplicateTitlesIgnoringCase() {
        TaskItem existing = new TaskItem(UUID.randomUUID(), "Prepare sprint review", "existing", com.metinkuzey.cleanapi.domain.model.TaskStatus.TODO, Instant.now());
        when(taskRepository.findByTitleIgnoreCase("prepare sprint review")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> taskService.createTask(new CreateTaskCommand("prepare sprint review", "new")))
                .isInstanceOf(DuplicateTaskException.class);
    }

    @Test
    void shouldThrowWhenTaskMissing() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(id))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldListTasksFromRepository() {
        TaskItem t1 = new TaskItem(UUID.randomUUID(), "A", "", com.metinkuzey.cleanapi.domain.model.TaskStatus.TODO, Instant.now());
        when(taskRepository.findAll()).thenReturn(List.of(t1));

        List<TaskItem> result = taskService.listTasks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("A");
    }
}

package com.metinkuzey.cleanapi.application.service;

import com.metinkuzey.cleanapi.application.port.in.CreateTaskCommand;
import com.metinkuzey.cleanapi.application.port.in.TaskUseCase;
import com.metinkuzey.cleanapi.application.port.out.TaskRepository;
import com.metinkuzey.cleanapi.domain.exception.DuplicateTaskException;
import com.metinkuzey.cleanapi.domain.exception.TaskNotFoundException;
import com.metinkuzey.cleanapi.domain.model.TaskItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService implements TaskUseCase {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskItem createTask(CreateTaskCommand command) {
        String normalizedTitle = command.title() == null ? "" : command.title().trim();
        taskRepository.findByTitleIgnoreCase(normalizedTitle)
                .ifPresent(existing -> {
                    throw new DuplicateTaskException(normalizedTitle);
                });

        TaskItem newTask = TaskItem.createNew(command.title(), command.description());
        return taskRepository.save(newTask);
    }

    @Override
    public TaskItem getTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Override
    public List<TaskItem> listTasks() {
        return taskRepository.findAll();
    }
}

package com.metinkuzey.cleanapi.application.port.out;

import com.metinkuzey.cleanapi.domain.model.TaskItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    TaskItem save(TaskItem task);

    Optional<TaskItem> findById(UUID taskId);

    Optional<TaskItem> findByTitleIgnoreCase(String title);

    List<TaskItem> findAll();
}

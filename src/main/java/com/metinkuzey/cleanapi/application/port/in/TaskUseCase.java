package com.metinkuzey.cleanapi.application.port.in;

import com.metinkuzey.cleanapi.domain.model.TaskItem;

import java.util.List;
import java.util.UUID;

public interface TaskUseCase {

    TaskItem createTask(CreateTaskCommand command);

    TaskItem getTaskById(UUID taskId);

    List<TaskItem> listTasks();
}

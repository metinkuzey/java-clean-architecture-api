package com.metinkuzey.cleanapi.rest.mapper;

import com.metinkuzey.cleanapi.domain.model.TaskItem;
import com.metinkuzey.cleanapi.rest.dto.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoMapper {

    public TaskResponse toResponse(TaskItem taskItem) {
        return new TaskResponse(
                taskItem.getId(),
                taskItem.getTitle(),
                taskItem.getDescription(),
                taskItem.getStatus(),
                taskItem.getCreatedAt()
        );
    }
}

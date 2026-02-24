package com.metinkuzey.cleanapi.rest.mapper;

import com.metinkuzey.cleanapi.domain.model.TaskItem;
import com.metinkuzey.cleanapi.domain.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TaskDtoMapperTest {

    private final TaskDtoMapper mapper = new TaskDtoMapper();

    @Test
    void shouldMapTaskItemToResponse() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();
        TaskItem task = new TaskItem(id, "Ship MVP", "Finalize docs", TaskStatus.IN_PROGRESS, now);

        var response = mapper.toResponse(task);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.title()).isEqualTo("Ship MVP");
        assertThat(response.description()).isEqualTo("Finalize docs");
        assertThat(response.status()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(response.createdAt()).isEqualTo(now);
    }
}

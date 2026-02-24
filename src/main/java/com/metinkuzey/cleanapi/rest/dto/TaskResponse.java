package com.metinkuzey.cleanapi.rest.dto;

import com.metinkuzey.cleanapi.domain.model.TaskStatus;
import java.time.Instant;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt
) {
}

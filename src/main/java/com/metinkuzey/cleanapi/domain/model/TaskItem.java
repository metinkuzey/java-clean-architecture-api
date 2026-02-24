package com.metinkuzey.cleanapi.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class TaskItem {

    private final UUID id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final Instant createdAt;

    public TaskItem(UUID id, String title, String description, TaskStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.title = requireText(title, "title is required");
        this.description = normalizeDescription(description);
        this.status = Objects.requireNonNull(status, "status is required");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
    }

    public static TaskItem createNew(String title, String description) {
        return new TaskItem(UUID.randomUUID(), title, description, TaskStatus.TODO, Instant.now());
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return "";
        }
        return description.trim();
    }
}

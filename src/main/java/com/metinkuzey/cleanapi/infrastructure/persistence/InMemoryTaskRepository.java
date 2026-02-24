package com.metinkuzey.cleanapi.infrastructure.persistence;

import com.metinkuzey.cleanapi.application.port.out.TaskRepository;
import com.metinkuzey.cleanapi.domain.model.TaskItem;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final ConcurrentMap<UUID, TaskItem> storage = new ConcurrentHashMap<>();

    @Override
    public TaskItem save(TaskItem task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<TaskItem> findById(UUID taskId) {
        return Optional.ofNullable(storage.get(taskId));
    }

    @Override
    public Optional<TaskItem> findByTitleIgnoreCase(String title) {
        return storage.values().stream()
                .filter(task -> task.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public List<TaskItem> findAll() {
        return storage.values().stream()
                .sorted(Comparator.comparing(TaskItem::getCreatedAt))
                .toList();
    }
}

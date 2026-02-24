package com.metinkuzey.cleanapi.rest;

import com.metinkuzey.cleanapi.application.port.in.CreateTaskCommand;
import com.metinkuzey.cleanapi.application.port.in.TaskUseCase;
import com.metinkuzey.cleanapi.rest.dto.CreateTaskRequest;
import com.metinkuzey.cleanapi.rest.dto.TaskResponse;
import com.metinkuzey.cleanapi.rest.mapper.TaskDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/tasks")
@Validated
@Tag(name = "Tasks", description = "Task operations for demo clean architecture flow")
public class TaskController {

    private final TaskUseCase taskUseCase;
    private final TaskDtoMapper taskDtoMapper;

    public TaskController(TaskUseCase taskUseCase, TaskDtoMapper taskDtoMapper) {
        this.taskUseCase = taskUseCase;
        this.taskDtoMapper = taskDtoMapper;
    }

    @PostMapping
    @Operation(summary = "Create a task")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        var created = taskUseCase.createTask(new CreateTaskCommand(request.title(), request.description()));
        var response = taskDtoMapper.toResponse(created);
        URI location = URI.create("/api/v1/tasks/" + created.getId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get a task by id")
    public ResponseEntity<TaskResponse> getTask(@PathVariable UUID taskId) {
        var task = taskUseCase.getTaskById(taskId);
        return ResponseEntity.ok(taskDtoMapper.toResponse(task));
    }

    @GetMapping
    @Operation(summary = "List tasks")
    public ResponseEntity<List<TaskResponse>> listTasks() {
        List<TaskResponse> responses = taskUseCase.listTasks().stream()
                .map(taskDtoMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}

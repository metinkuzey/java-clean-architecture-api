package com.metinkuzey.cleanapi.rest.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
        @NotBlank(message = "title must not be blank")
        @Size(max = 120, message = "title must be at most 120 characters")
        String title,

        @Size(max = 500, message = "description must be at most 500 characters")
        String description
) {
}

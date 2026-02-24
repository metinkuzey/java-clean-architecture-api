package com.metinkuzey.cleanapi.rest.dto;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        List<String> details,
        String path
) {
}

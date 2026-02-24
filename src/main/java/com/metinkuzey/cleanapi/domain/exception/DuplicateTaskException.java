package com.metinkuzey.cleanapi.domain.exception;

public class DuplicateTaskException extends RuntimeException {

    public DuplicateTaskException(String title) {
        super("Task with title already exists: " + title);
    }
}

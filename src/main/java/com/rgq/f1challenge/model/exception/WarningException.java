package com.rgq.f1challenge.model.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class WarningException extends Exception {
    private final ResponseEntity<Object> response;

    public WarningException(String message, ResponseEntity<Object> response) {
        super(message);
        this.response = response;
    }
}

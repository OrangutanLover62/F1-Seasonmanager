package com.rgq.f1challenge.model.exception;

import org.springframework.http.ResponseEntity;

public class NoValueException extends WarningException {

    public NoValueException(String message) {
        super(message, ResponseEntity.badRequest().build());
    }
}

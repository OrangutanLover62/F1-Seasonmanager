package com.rgq.f1challenge.model.exception;

import org.springframework.http.ResponseEntity;

public class NoResultsException extends WarningException {

    public NoResultsException(String message) {
        super(message, ResponseEntity.notFound().build());
    }
}

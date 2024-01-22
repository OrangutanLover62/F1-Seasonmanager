package com.f1manager.model.exception;

import com.f1manager.model.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UnauthorizedException extends WarningException {

    public UnauthorizedException(String message) {
        super(message, ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    public UnauthorizedException(UserDto user) {
        super(String.format("Invalid credentials -> username: %s, password: %s",
            user.getUsername(), user.getPassword()
        ), ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}

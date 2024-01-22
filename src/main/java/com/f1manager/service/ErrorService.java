package com.f1manager.service;

import com.f1manager.controller.web.NavigationController;
import com.f1manager.model.exception.WarningException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {
    private static final Logger logger = LoggerFactory.getLogger(ErrorService.class);

    public void handle(Exception e, HttpServletResponse response) {
        handle(e);
        try {
            response.sendRedirect(NavigationController.pathLogin);
        } catch (Exception e2) {
            handle(e2);
        }
    }

    public ResponseEntity<Object> handle(Exception e) {
        if (e instanceof WarningException) {
            logger.warn(e.getMessage());
            return ((WarningException) e).getResponse();
        } else {
            logger.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity
                .internalServerError()
                .build();
        }
    }
}

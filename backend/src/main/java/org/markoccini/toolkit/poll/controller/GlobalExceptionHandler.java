package org.markoccini.toolkit.poll.controller;

import jakarta.validation.ConstraintViolationException;
import org.markoccini.toolkit.common.exceptions.BadRequestException;
import org.markoccini.toolkit.common.exceptions.DatabaseException;
import org.markoccini.toolkit.common.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler({
            BadRequestException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<Map<String, String>> handleBadRequestExceptions(Exception ex) {
        return  ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler({
            ServerErrorException.class,
    })
    public ResponseEntity<Map<String, String>> handleServerErrorExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler({
            DatabaseException.class,
    })
    public ResponseEntity<Map<String, String>> handleDatabaseExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", ex.getMessage()));
    }
}

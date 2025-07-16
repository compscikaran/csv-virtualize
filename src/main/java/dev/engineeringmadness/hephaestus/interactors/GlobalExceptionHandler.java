package dev.engineeringmadness.hephaestus.interactors;

import dev.engineeringmadness.hephaestus.core.domain.ErrorResponse;
import dev.engineeringmadness.hephaestus.core.domain.QueryExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QueryExecutionException.class)
    public ResponseEntity<String> handleQueryFailure(QueryExecutionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Failed to execute supplied query", ex.getMessage()).toString());
    }
}

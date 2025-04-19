package dev.engineeringmadness.hephaestus.interactors;

import dev.engineeringmadness.hephaestus.core.domain.ErrorResponse;
import dev.engineeringmadness.hephaestus.core.domain.QueryExecutionException;
import dev.engineeringmadness.hephaestus.plugins.PluginExecutionException;
import dev.engineeringmadness.hephaestus.plugins.validator.QueryValidationException;
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

    @ExceptionHandler(PluginExecutionException.class)
    public ResponseEntity<String> handlePluginFailure(PluginExecutionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Failed to apply plugin prior to query execution", ex.getMessage()).toString());
    }

    @ExceptionHandler(QueryValidationException.class)
    public ResponseEntity<String> handleValidationFailure(QueryValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Query Invalid:", ex.getMessage()).toString());
    }
}

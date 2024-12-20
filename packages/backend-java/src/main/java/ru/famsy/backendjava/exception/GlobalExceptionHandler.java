package ru.famsy.backendjava.exception;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        Map<String, String> errorResponse = new HashMap<>();

        if (message.contains("unique constraint")) {
            String fieldName = extractFieldNameFromMessage(message);
            errorResponse.put(fieldName, "Значение для этого поля уже существует.");
        } else {
            errorResponse.put("error", "Нарушение целостности данных.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    private String extractFieldNameFromMessage(String message) {
        int startIndex = message.indexOf("(") + 1;
        int endIndex = message.indexOf(")");
        return message.substring(startIndex, endIndex);
    }
}
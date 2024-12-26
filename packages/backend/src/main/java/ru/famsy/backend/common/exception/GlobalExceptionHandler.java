package ru.famsy.backend.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.famsy.backend.common.exception.base.ConflictException;
import ru.famsy.backend.common.exception.base.ForbiddenException;
import ru.famsy.backend.common.exception.base.NotFoundException;
import ru.famsy.backend.common.exception.base.ValidationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        Map<String, String> errors = new HashMap<>();

        // TODO: Написать нормальную логику обработки.
        if (message.contains("violates foreign key constraint")) {
            errors.put("error", "Foreign Key Violation");
            errors.put("message", "Невозможно удалить запись, так как существуют связанные данные");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }

        if (message.contains("unique constraint") || message.contains("duplicate key")) {
            String fieldName = extractFieldNameFromMessage(message);
            errors.put("error", "Unique Constraint Violation");
            errors.put("message", "Поле '" + fieldName + "' должно быть уникальным");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
        }

        errors.put("error", "Data Integrity Violation");
        errors.put("message", "Нарушение целостности данных");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    private String extractFieldNameFromMessage(String message) {
        int startIndex = message.indexOf("(") + 1;
        int endIndex = message.indexOf(")");
        return message.substring(startIndex, endIndex);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Validation exception");
        errors.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, String>> handleConflictException(ConflictException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Conflict");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getClass().getSimpleName());
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, String>> handleForbiddenException(ForbiddenException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Forbidden");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
    }
}
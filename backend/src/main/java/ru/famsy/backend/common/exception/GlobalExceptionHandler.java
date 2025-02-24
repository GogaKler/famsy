package ru.famsy.backend.common.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.famsy.backend.common.exception.base.*;
import ru.famsy.backend.common.exception.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        // TODO: Написать нормальную логику обработки.
        if (message.contains("violates foreign key constraint")) {
            return new ErrorResponse("Foreign key violation", "Невозможно удалить запись, так как существуют связанные данные");
        }

        if (message.contains("unique constraint") || message.contains("duplicate key")) {
            String fieldName = extractFieldNameFromMessage(message);
            return new ErrorResponse("UniqueConstraintViolation", "Поле '" + fieldName + "' должно быть уникальным");
        }

        return new ErrorResponse("Data Integrity Violation", "Нарушение целостности данных");
    }

    private String extractFieldNameFromMessage(String message) {
        int startIndex = message.indexOf("(") + 1;
        int endIndex = message.indexOf(")");
        return message.substring(startIndex, endIndex);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
      return new ErrorResponse("Validation exception", ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(ConflictException ex) {
      return new ErrorResponse("Conflict", ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
      return new ErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException ex) {
      return new ErrorResponse("Forbidden", ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
      return new ErrorResponse("Unauthorized", ex.getMessage());
    }

    @ExceptionHandler(MinioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMinioException(MinioException ex) {
      return new ErrorResponse("Minio exception", ex.getMessage());
    }

    @ExceptionHandler({
        ConverterNotFoundException.class,
        ConversionFailedException.class,
        MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConversionExceptions(Exception ex) {
      return new ErrorResponse("Bad Request", "Некорректные параметры запроса: " + ex.getCause().getMessage());
    }
}
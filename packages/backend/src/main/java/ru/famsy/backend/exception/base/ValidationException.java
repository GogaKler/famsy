package ru.famsy.backend.exception.base;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}

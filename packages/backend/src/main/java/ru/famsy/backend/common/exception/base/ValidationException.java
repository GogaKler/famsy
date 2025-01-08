package ru.famsy.backend.common.exception.base;

public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}

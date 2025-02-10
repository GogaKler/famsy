package ru.famsy.backend.common.exception.base;

public class MinioException extends RuntimeException {
  public MinioException(String message) {
    super(message);
  }
}

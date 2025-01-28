package ru.famsy.backend.modules.auth.exception;

import ru.famsy.backend.common.exception.base.ValidationException;

public class AuthValidationException extends ValidationException {
  public AuthValidationException(String message) {
    super(message);
  }
}

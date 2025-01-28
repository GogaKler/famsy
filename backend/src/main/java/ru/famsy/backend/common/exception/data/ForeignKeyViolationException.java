package ru.famsy.backend.common.exception.data;

import ru.famsy.backend.common.exception.base.ConflictException;

public class ForeignKeyViolationException extends ConflictException {
  public ForeignKeyViolationException(String message) {
    super(message);
  }
}

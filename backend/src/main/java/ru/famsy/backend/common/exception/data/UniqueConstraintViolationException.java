package ru.famsy.backend.common.exception.data;

import ru.famsy.backend.common.exception.base.ConflictException;

public class UniqueConstraintViolationException extends ConflictException {
  private final String field;

  public UniqueConstraintViolationException(String field) {
    super("Значение для поля " + field + " уже существует.");
    this.field = field;
  }

  public String getField() {
    return field;
  }
}

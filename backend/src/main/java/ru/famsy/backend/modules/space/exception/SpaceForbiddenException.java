package ru.famsy.backend.modules.space.exception;

import ru.famsy.backend.common.exception.base.ForbiddenException;

public class SpaceForbiddenException extends ForbiddenException {
  public SpaceForbiddenException(String message) {
    super(message);
  }

  static public SpaceForbiddenException notAccess() {
      return new SpaceForbiddenException("У вас нет доступа к этому пространству.");
  }

  static public SpaceForbiddenException notAccessUpdate() {
    return new SpaceForbiddenException("У вас нет прав на редактирование пространства.");
  }
}

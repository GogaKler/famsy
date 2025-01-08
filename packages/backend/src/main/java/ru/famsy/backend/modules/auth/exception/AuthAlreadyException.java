package ru.famsy.backend.modules.auth.exception;

import ru.famsy.backend.common.exception.base.ForbiddenException;

public class AuthAlreadyException extends ForbiddenException {
  public AuthAlreadyException() {
    super("Пользователь уже авторизован");
  }
}

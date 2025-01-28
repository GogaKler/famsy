package ru.famsy.backend.modules.user_session.exception;

import ru.famsy.backend.common.exception.base.UnauthorizedException;

public class SessionExpiredException extends UnauthorizedException {
  public SessionExpiredException(String message) {
    super(message);
  }
}

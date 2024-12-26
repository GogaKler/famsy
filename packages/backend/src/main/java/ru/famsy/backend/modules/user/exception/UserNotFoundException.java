package ru.famsy.backend.modules.user.exception;

import ru.famsy.backend.common.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String message) {
    super(message);
  }

  public static UserNotFoundException notFound() {
    return new UserNotFoundException("Пользователь не найден");
  }

  public static UserNotFoundException byId(Long id) {
    return new UserNotFoundException("Пользователь с таким id: " + id + " не найден");
  }
}

package ru.famsy.backend.modules.jwt.exception;

public class TokenExpiredException extends JwtException {
  public TokenExpiredException() {
      super("Токен истёк");
  }
}
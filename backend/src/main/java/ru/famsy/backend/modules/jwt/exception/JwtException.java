package ru.famsy.backend.modules.jwt.exception;

import ru.famsy.backend.common.exception.base.UnauthorizedException;

/**
 * Базовое исключение для всех JWT-related ошибок
 */
public class JwtException extends UnauthorizedException {
    public JwtException(String message) {
        super(message);
    }
    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
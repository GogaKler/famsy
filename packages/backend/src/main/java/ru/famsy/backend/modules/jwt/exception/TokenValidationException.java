package ru.famsy.backend.modules.jwt.exception;

public class TokenValidationException extends JwtException {
    public TokenValidationException(String message) {
        super(message);
    }
}
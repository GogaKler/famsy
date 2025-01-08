package ru.famsy.backend.modules.jwt.exception;

public class TokenMalformedException extends JwtException {
    public TokenMalformedException() {
        super("Неверная структура JWT токена");
    }
}
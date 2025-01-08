package ru.famsy.backend.modules.jwt.exception;

public class TokenSignatureException extends JwtException {
    public TokenSignatureException() {
        super("Неверная сигнатура токена");
    }
}
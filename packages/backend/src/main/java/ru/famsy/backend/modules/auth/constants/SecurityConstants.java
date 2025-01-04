package ru.famsy.backend.modules.auth.constants;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SecurityConstants {
    public static final Duration ACCESS_TOKEN_EXPIRATION = Duration.of(30, ChronoUnit.SECONDS);
    public static final Duration REFRESH_TOKEN_EXPIRATION = Duration.of(30, ChronoUnit.SECONDS);
    public static final Duration SESSION_EXPIRATION = REFRESH_TOKEN_EXPIRATION;
    // TODO: Only Dev. Убрать в секреты
    public static final String JWT_SECRET = "5e9366349290a82f53e81b2c359f9554a3c291b284e482ab3c3aab98d3e044b9";
    public static final int MAX_SESSIONS_PER_USER = 5;

    public static final String[] PUBLIC_PATHS = {
            "/auth/**",
            "/error",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
}

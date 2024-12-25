package ru.famsy.backend.modules.auth.config;

public class SecurityConstants {
    public static final long ACCESS_TOKEN_EXPIRATION = 300000;
    public static final long REFRESH_TOKEN_EXPIRATION = 604800000;
    public static final String JWT_SECRET = "5e9366349290a82f53e81b2c359f9554a3c291b284e482ab3c3aab98d3e044b9";
    public static final int MAX_DEVICES_PER_USER = 5;

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
}

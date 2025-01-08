package ru.famsy.backend.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class SecurityConfigProperties {
    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;
    private final Duration sessionExpiration;
    private final String jwtSecret;
    public static final int MAX_SESSIONS_PER_USER = 5;

    public static final String[] PUBLIC_PATHS = {
            "/auth/**",
            "/error",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public SecurityConfigProperties(
            @Value("${famsy.security.jwt.access-token.expiration}") Duration accessTokenExpiration,
            @Value("${famsy.security.jwt.refresh-token.expiration}") Duration refreshTokenExpiration,
            @Value("${famsy.security.jwt.session.expiration}") Duration sessionExpiration,
            @Value("${famsy.security.jwt.secret}") String jwtSecret
    ) {
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.sessionExpiration = sessionExpiration;
        this.jwtSecret = jwtSecret;
    }

    public Duration getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public Duration getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public Duration getSessionExpiration() {
        return sessionExpiration;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }
} 
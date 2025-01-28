package ru.famsy.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.famsy.backend.common.audit.AuditorAwareImplementation;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImplementation();
  }
}

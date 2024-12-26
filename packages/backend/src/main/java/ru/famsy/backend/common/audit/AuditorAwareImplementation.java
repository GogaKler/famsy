package ru.famsy.backend.common.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImplementation implements AuditorAware<String> {

  // NOTE: Определяем, что подставлять в created_by и modified_by у сущностей.
  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.of("SYSTEM");
    }

    return Optional.of(authentication.getName());
  }
}

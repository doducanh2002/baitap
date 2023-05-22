package org.up.finance.configuration.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

import static org.up.finance.constant.FinanceConstant.AuditorConstant.ANONYMOUS;
import static org.up.finance.constant.FinanceConstant.AuditorConstant.SYSTEM;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (Objects.nonNull(authentication) && !isAnonymous()) {
      return Optional.ofNullable(authentication.getCredentials().toString());
    }
    return Optional.of(SYSTEM);
  }

  private boolean isAnonymous() {
    return SecurityContextHolder.getContext().getAuthentication().getName().equals(ANONYMOUS);
  }
}

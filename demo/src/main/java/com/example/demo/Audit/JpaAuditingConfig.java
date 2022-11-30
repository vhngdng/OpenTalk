package com.example.demo.Audit;

import com.example.demo.security.SecureService.UserDetailImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAware() {
      @Override
      public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
          return Optional.ofNullable(userDetail.getUsername());
        }
        return Optional.empty();
      }
    };
  }


}

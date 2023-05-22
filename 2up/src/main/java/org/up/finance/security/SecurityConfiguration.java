package org.up.finance.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.up.finance.filter.CorsCustomFilter;
import org.up.finance.security.error.UnAuthenticationCustomHandler;
import org.up.finance.security.error.UnAuthorizationCustomHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CorsCustomFilter corsCustomFilter;
  private final UnAuthenticationCustomHandler unAuthenticationCustomHandler;
  private final UnAuthorizationCustomHandler unAuthorizationCustomHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
          .csrf().disable()
          .authorizeHttpRequests()
          .requestMatchers("/api/v1/auth/users/register").permitAll()
          .requestMatchers("/api/v1/auth/users/active").permitAll()
          .requestMatchers("/api/v1/auth/otp/resend").permitAll()
          .requestMatchers("/api/v1/auth/login").permitAll()
          .requestMatchers("/swagger-ui**", "/swagger-ui/**").permitAll()
          .requestMatchers("/api-docs**", "/api-docs/**").permitAll()
          .anyRequest().authenticated()
          .and()
          .exceptionHandling()
          .accessDeniedHandler(unAuthorizationCustomHandler)
          .authenticationEntryPoint(unAuthenticationCustomHandler)
          .and().sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
  }
}

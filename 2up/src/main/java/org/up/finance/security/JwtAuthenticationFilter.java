package org.up.finance.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.up.finance.service.AuthenticationTokenService;
import org.up.finance.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.up.finance.constant.FinanceConstant.AuthConstant.*;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationTokenService authenticationTokenService;
  private final UserService userService;

  private static final List<String> PERMIT_ALL_REQUEST = List.of(
      "/api/v1/auth/users/register",
      "/api/v1/auth/users/active",
      "/api/v1/auth/otp/resend",
      "/api/v1/auth/login",
      "/swagger-ui.html",
      "/swagger-ui/index.html",
      "/api-docs",
      "/api-docs/swagger-config"
  );

  public JwtAuthenticationFilter(AuthenticationTokenService authenticationTokenService, UserService userService) {
    this.authenticationTokenService = authenticationTokenService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
  ) throws ServletException, IOException {
    log.info(
          "(doFilterInternal)request: {}, response: {}, filterChain: {}",
          request,
          response,
          filterChain);
    String accessToken = request.getHeader(AUTHORIZATION);
    if (PERMIT_ALL_REQUEST.contains(request.getContextPath())) {
      filterChain.doFilter(request, response);
      return;
    }
    if (Objects.isNull(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }
    if (!accessToken.startsWith(TYPE_TOKEN)) {
      filterChain.doFilter(request, response);
      return;
    }
    var jwtToken = accessToken.substring(SIZE_DIGIT_TYPE_TOKEN);

    try {
      String userId = authenticationTokenService.getSubjectFromAccessToken(jwtToken);
      if (Objects.nonNull(userId) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
        var user = userService.get(userId);

        if (authenticationTokenService.validateAccessToken(jwtToken, userId)) {
          var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getId(), new ArrayList<>()
          );
          usernamePasswordAuthenticationToken.setDetails(user);
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    } catch (Exception ex) {
      log.error("(doFilterInternal) tokenInvalid: {}", jwtToken);
    }
    filterChain.doFilter(request, response);
  }
}

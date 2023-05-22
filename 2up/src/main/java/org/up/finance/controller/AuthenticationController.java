package org.up.finance.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.up.finance.dto.FinanceResponse;
import org.up.finance.dto.request.ActiveUserRequest;
import org.up.finance.dto.request.LoginRequest;
import org.up.finance.dto.request.ResendOTPRequest;
import org.up.finance.dto.request.UserRegisterRequest;
import org.up.finance.dto.response.LoginResponse;
import org.up.finance.dto.response.UserRegisterResponse;
import org.up.finance.facade.AuthenticationFacadeService;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final AuthenticationFacadeService authenticationFacadeService;

  public AuthenticationController(AuthenticationFacadeService authenticationFacadeService) {
    this.authenticationFacadeService = authenticationFacadeService;
  }

  @PostMapping("/users/active")
  @ResponseStatus(HttpStatus.OK)
  public FinanceResponse active(@Valid @RequestBody ActiveUserRequest request) {
    log.info("(active) request:{}", request);
    authenticationFacadeService.activeUser(request);
    return FinanceResponse.successOf(HttpStatus.OK.value());
  }

  @PostMapping("/users/register")
  @ResponseStatus(HttpStatus.CREATED)
  public FinanceResponse<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
    log.info("(register) request:{}", request);
    return FinanceResponse.successOf(
          HttpStatus.CREATED.value(),
          authenticationFacadeService.register(request)
    );
  }

  @PostMapping("/login")
  public FinanceResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    log.info("(login) request: {}", request);
    return FinanceResponse.successOf(
          HttpStatus.OK.value(),
          authenticationFacadeService.login(request)
    );
  }

  @PostMapping("/otp/resend")
  @ResponseStatus(HttpStatus.OK)
  public FinanceResponse resendOTP(@Valid @RequestBody ResendOTPRequest request) {
    log.info("(active) request:{}", request);
    authenticationFacadeService.resendOTP(request);
    return FinanceResponse.successOf(HttpStatus.OK.value());
  }
}

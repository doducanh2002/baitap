package org.up.finance.facade.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.up.finance.dto.request.ActiveUserRequest;
import org.up.finance.dto.request.LoginRequest;
import org.up.finance.dto.request.ResendOTPRequest;
import org.up.finance.dto.request.UserRegisterRequest;
import org.up.finance.dto.response.LoginResponse;
import org.up.finance.dto.response.UserRegisterResponse;
import org.up.finance.entity.User;
import org.up.finance.exception.PasswordInvalidException;
import org.up.finance.exception.UserActivatedException;
import org.up.finance.exception.UserNotActivatedException;
import org.up.finance.facade.AuthenticationFacadeService;
import org.up.finance.service.AuthenticationTokenService;
import org.up.finance.service.EmailService;
import org.up.finance.service.OTPService;
import org.up.finance.service.UserService;
import org.up.finance.service.*;
import org.up.finance.util.BCryptUtils;
import org.up.finance.util.GeneratorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.up.finance.constant.FinanceConstant.EmailConstant.*;
import static org.up.finance.constant.FinanceConstant.OTPConstant.OTP_TEMPLATE_NAME;
import static org.up.finance.constant.FinanceConstant.TokenConstant.KEY_CACHE_REFRESH_TOKEN;

@Slf4j
public class AuthenticationFacadeServiceImpl implements AuthenticationFacadeService {
  private final UserService userService;
  private final OTPService otpService;
  private final EmailService emailService;
  private final Long accessTokenLifeTime;
  private final Long refreshTokenLifeTime;
  private final AuthenticationTokenService authenticationTokenService;
  private final TokenRedisService tokenRedisService;

  public AuthenticationFacadeServiceImpl(
        UserService userService,
        OTPService otpService,
        EmailService emailService,
        Long accessTokenLifeTime,
        Long refreshTokenLifeTime,
        AuthenticationTokenService authenticationTokenService,
        TokenRedisService tokenRedisService
  ) {
    this.userService = userService;
    this.otpService = otpService;
    this.emailService = emailService;
    this.accessTokenLifeTime = accessTokenLifeTime;
    this.refreshTokenLifeTime = refreshTokenLifeTime;
    this.authenticationTokenService = authenticationTokenService;
    this.tokenRedisService = tokenRedisService;
  }


  @Override
  public void activeUser(ActiveUserRequest request) {
    log.info("(activeUser) request:{}", request);

    userService.existsByEmail(request.getEmail());
    if (otpService.verify(request.getEmail(), request.getOtp())) {
      userService.active(request.getEmail());
    }
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    log.info("(login) request: {}", request);
    User user = userService.findByEmail(request.getEmail());

    if (!user.isActivated()) {
      log.error("(login) email: {} -> UserNotActivatedException", request.getEmail());
      throw new UserNotActivatedException(request.getEmail());
    }

    if (!BCryptUtils.getPasswordEncode().matches(request.getPassword(), user.getPassword())) {
      log.error("(login) password: {} -> PasswordInvalidException", request.getPassword());
      throw new PasswordInvalidException();
    }

    String accessToken = authenticationTokenService.generateAccessToken(
          user.getId(),
          user.getEmail(),
          user.getUsername(),
          user.getFullName()
    );
    String refreshToken = authenticationTokenService.generateRefreshToken(
          user.getId(),
          user.getEmail(),
          user.getUsername(),
          user.getFullName()
    );
    tokenRedisService.set(KEY_CACHE_REFRESH_TOKEN, user.getId(), refreshToken);
    authenticate(user);
    return LoginResponse.of(
          accessToken,
          refreshToken,
          accessTokenLifeTime,
          refreshTokenLifeTime
    );
  }

  @Override
  @Transactional
  public UserRegisterResponse register(UserRegisterRequest request) {
    log.info("(register) request:{}", request);

    String passwordEncrypt = BCryptUtils.getPasswordEncode().encode(request.getPassword());
    User user = userService.create(
          request.getUsername(),
          passwordEncrypt,
          request.getEmail(),
          request.getFullName()
    );

    String optActiveUser = GeneratorUtils.generateOTP();
    otpService.set(request.getEmail(), optActiveUser);

    sendEmailOTPTemplate(
          user.getEmail(),
          optActiveUser,
          KEY_PARAM_OTP_TIME_LIFE,
          KEY_PARAM_OTP,
          SUBJECT_REGISTER_USER
    );

    return UserRegisterResponse.of(
          user.getId(),
          user.getEmail(),
          user.getUsername(),
          user.getFullName(),
          user.isActivated()
    );
  }

  @Override
  public void resendOTP(ResendOTPRequest request) {
    log.info("(resendOTP) request:{}", request);

    this.validateResendOTPRequestByEmail(request.getEmail());
    String optActiveUser = GeneratorUtils.generateOTP();
    otpService.set(request.getEmail(), optActiveUser);

    sendEmailOTPTemplate(
          request.getEmail(),
          optActiveUser,
          KEY_PARAM_OTP_TIME_LIFE,
          KEY_PARAM_OTP,
          SUBJECT_REGISTER_USER
    );

  }

  private void authenticate(User user) {
    var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          user.getEmail(),
          user.getId(),
          new ArrayList<>()
    );
    usernamePasswordAuthenticationToken.setDetails(user);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  private void sendEmailOTPTemplate(
        String email, String otp, String keyParamTimeLife, String keyParamOtp, String subject) {
    log.info(
          "(sendEmailOTPTemplate)email: {}, otp: {}, keyParamTimeLife: {}, keyParamOtp: {}, subject: {}",
          email,
          otp,
          keyParamTimeLife,
          keyParamOtp,
          subject
    );
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(keyParamTimeLife, 5);
    params.put(keyParamOtp, otp);
    emailService.send(subject, email, OTP_TEMPLATE_NAME, params);
  }

  private void validateResendOTPRequestByEmail(String email) {
    log.info("(validateResendOTPRequestByEmail) email: {}", email);

    userService.existsByEmail(email);
    if (userService.checkActivated(email)) {
      log.error("(validateResendOTPRequestByEmail) email: {}", email);
      throw new UserActivatedException(email);
    }
  }
}

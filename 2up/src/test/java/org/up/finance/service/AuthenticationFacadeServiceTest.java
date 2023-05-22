package org.up.finance.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.dto.request.ActiveUserRequest;
import org.up.finance.dto.request.LoginRequest;
import org.up.finance.dto.request.UserRegisterRequest;
import org.up.finance.dto.response.LoginResponse;
import org.up.finance.entity.User;
import org.up.finance.exception.EmailNotFoundException;
import org.up.finance.exception.OTPBadRequestException;
import org.up.finance.exception.OTPNotFoundException;
import org.up.finance.facade.AuthenticationFacadeService;
import org.up.finance.repository.UserRepository;
import org.up.finance.util.BCryptUtils;

import java.util.Optional;


@WebMvcTest(AuthenticationFacadeService.class)
@ContextConfiguration(classes = {
      FinanceServiceTestConfiguration.class,
      RedisConfiguration.class}
)
public class AuthenticationFacadeServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserService userService;
  @Autowired
  private OTPService otpService;
  @Autowired
  private EmailService emailService;
  @Autowired
  private AuthenticationFacadeService authenticationFacadeService;

  private User mockUserEntity() {
    return User.of(
          "luatnq",
          "Luat@123",
          "luatnq@gmail.com",
          "Nguyen Quoc Luat"
    );
  }

  private UserRegisterRequest mockUserRegisterRequestValid() {
    return UserRegisterRequest.from(
          "dajktatj123@gmail.com",
          "luatnq",
          "Nguyen Quoc Luat",
          "Luat@123",
          "Luat@123"
    );
  }

  private ActiveUserRequest mockActiveUserRequestValid() {
    return ActiveUserRequest.of(
          "dajktatj123@gmail.com",
          "1234"
    );
  }

  private LoginRequest mockLoginRequest() {
    return LoginRequest.of(
          "dajktatj123@gmail.com",
          "Luat@123"
    );
  }


  @Test
  public void testActiveUser_WhenEmailNotExist_ThrowEmailNotFoundException() {
    ActiveUserRequest request = mockActiveUserRequestValid();

    Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    Mockito.when(userService.existsByEmail(request.getEmail())).thenThrow(EmailNotFoundException.class);
    Assertions.assertThrowsExactly(EmailNotFoundException.class, () -> authenticationFacadeService.activeUser(request));
  }

  @Test
  public void testActiveUser_WhenOTPExpired_ThrowOTPNotFoundException() {
    ActiveUserRequest request = mockActiveUserRequestValid();

    Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
    Mockito.when(userService.existsByEmail(request.getEmail())).thenReturn(true);

    Assertions.assertThrowsExactly(OTPNotFoundException.class, () -> authenticationFacadeService.activeUser(request));
  }

  @Test
  public void testActiveUser_WhenOTPInvalid_ThrowOTPBadRequestException() {
    ActiveUserRequest request = mockActiveUserRequestValid();

    Mockito.when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
    Mockito.when(userService.existsByEmail(request.getEmail())).thenReturn(true);
    otpService.set(request.getEmail(), "5678");
    Assertions.assertThrowsExactly(OTPBadRequestException.class, () -> authenticationFacadeService.activeUser(request));
    otpService.delete(request.getEmail());
  }

  @Test
  public void testCaseLogin_WhenEmailUnregistered_ThrowEmailNotFound() {
    User entity = mockUserEntity();
    String passwordEncode = BCryptUtils.getPasswordEncode().encode(entity.getPassword());
    entity.setActivated(true);
    entity.setPassword(passwordEncode);

    Mockito.when(userRepository.findByEmail(entity.getEmail())).thenReturn(null);
    Mockito.when(userService.findByEmail(entity.getEmail())).thenReturn(entity);

    Assertions.assertThrowsExactly(
          EmailNotFoundException.class,
          () -> authenticationFacadeService.login(mockLoginRequest())
    );
  }

}

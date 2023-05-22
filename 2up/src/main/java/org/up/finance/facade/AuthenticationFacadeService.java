package org.up.finance.facade;

import org.up.finance.dto.request.ActiveUserRequest;
import org.up.finance.dto.request.LoginRequest;
import org.up.finance.dto.request.ResendOTPRequest;
import org.up.finance.dto.request.UserRegisterRequest;
import org.up.finance.dto.response.LoginResponse;
import org.up.finance.dto.response.UserRegisterResponse;

public interface AuthenticationFacadeService {

  /**
   * active user
   *
   * @param request - activated user information
   */
  void activeUser(ActiveUserRequest request);

  /**
   * register user
   *
   * @param request - registered user information
   * @return - recently registered user information
   */
  UserRegisterResponse register(UserRegisterRequest request);

  /**
   * resend OTP for user's email registered
   *
   * @param request - request contain user's email registered
   */
  void resendOTP(ResendOTPRequest request);

  /**
   * login to system
   * @param request - contain email & password of user
   * @return access token & refresh token
   */
  LoginResponse login(LoginRequest request);
}

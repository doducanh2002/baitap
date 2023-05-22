package org.up.finance.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FinanceConstant {
  public static final String UTF_8_ENCODING = "UTF-8";

  public static class MessageResponse {
    public static String SUCCESS = "success";
    public static String MESSAGE_FOLDER = "classpath:messages/messages";
  }

  public static class AuthConstant {
    public static String TYPE_TOKEN = "Bearer ";
    public static String AUTHORIZATION = "Authorization";
    public static int SIZE_DIGIT_TYPE_TOKEN = 7;
    public static String UNAUTHENTICATED = "UnAuthenticated";
    public static int UNAUTHENTICATED_STATUS = 401;
  }

  public static class AuditorConstant {
    public static final String ANONYMOUS = "anonymousUser";
    public static final String SYSTEM = "SYSTEM";
  }

  public static class ValidationMessage {
    public static final String INVALID_USERNAME = "Username doesn't contain special characters";
    public static final String INVALID_PASSWORD = "Password must be at least 8 characters and contain 1 number, 1 capital letter and 1 special character";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String CONFIRM_PASSWORD_NOT_MATCH = "Doesn't match password";

    public static final String INVALID_OTP = "OTP only has 4 digit number";
  }

  public static class OTPConstant {
    public static final String FOUR_DIGITS_STRING = "0000";
    public static final Integer FOUR_DIGITS_UPPER_BOUND = 10000;
    public static final String OTP_TEMPLATE_NAME = "OTP-template";
  }

  public static class EmailConstant {
    public static final String SUBJECT_REGISTER_USER = "Personal Finance: Register user!";
    public static final String KEY_PARAM_OTP_TIME_LIFE = "time_life";
    public static final String KEY_PARAM_OTP = "otp";
    public static final String EMAIL_TEMPLATE_PREFIX = "/templates/";
    public static final String EMAIL_TEMPLATE_SUFFIX = ".html";
    public static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
  }

  public static class TokenConstant {
    public static final String KEY_CACHE_ACCESS_TOKEN = "access_token";
    public static final String KEY_CACHE_REFRESH_TOKEN = "refresh_token";
  }

  public static class ContentType {
    public static final String JSON_APPLICATION = "application/json;charset=UTF-8";
  }
}

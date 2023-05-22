package org.up.finance.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptUtils {
  private static final BCryptPasswordEncoder B_CRYPT_ENCODER = new BCryptPasswordEncoder();

  public static PasswordEncoder getPasswordEncode() {
    return B_CRYPT_ENCODER;
  }
}

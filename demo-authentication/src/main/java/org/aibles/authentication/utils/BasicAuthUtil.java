package org.aibles.authentication.utils;

import org.apache.commons.codec.binary.Base64;

public class BasicAuthUtil {
  private static final Base64 base64 = new Base64();

  private static final int USERNAME_INDEX = 0;

  private static final int PASSWORD_INDEX = 1;

  private static final String SPLITTER = ":";
  private static BasicAuthUtil instance;

  private BasicAuthUtil() {}

  public static BasicAuthUtil getInstance() {
    if (instance == null) {
      instance = new BasicAuthUtil();
    }

    return instance;
  }

  private String decodeToken(String token) {
    return new String(base64.decode(token.getBytes()));
  }

  public String getUsernameFromToken(String token) {
    return decodeToken(token).split(SPLITTER)[USERNAME_INDEX];
  }

  public String getPasswordFromToken(String token) {
    return decodeToken(token).split(SPLITTER)[PASSWORD_INDEX];
  }
}

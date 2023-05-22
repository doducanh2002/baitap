package org.up.finance.exception;

import org.up.finance.exception.base.NotFoundException;

public class OTPNotFoundException extends NotFoundException {

  public OTPNotFoundException(String otp, String message) {
    setCode("org.up.finance.exception.OtpNotFoundException");
    addParam("otp", otp);
    setMessage(message);
  }
}

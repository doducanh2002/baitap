package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class OTPBadRequestException extends BadRequestException {

  public OTPBadRequestException(String otp, String message) {
    setCode("org.up.finance.exception.OTPBadRequestException");
    addParam("otp", otp);
    setMessage(message);
  }
}

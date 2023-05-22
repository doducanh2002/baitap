package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class PasswordInvalidException extends BadRequestException {

  public PasswordInvalidException() {
    setCode("org.up.finance.exception.PasswordInvalidException");
  }
}

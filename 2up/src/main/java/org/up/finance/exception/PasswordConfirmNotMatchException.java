package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class PasswordConfirmNotMatchException extends BadRequestException {
  public PasswordConfirmNotMatchException() {
    setCode("org.up.finance.exception.PasswordConfirmNotMatchException");
  }
}

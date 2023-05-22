package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class UserNotActivatedException extends BadRequestException {

  public UserNotActivatedException(String email) {
    setCode("org.up.finance.exception.UserNotActivatedException");
    addParam("email", email);
  }
}

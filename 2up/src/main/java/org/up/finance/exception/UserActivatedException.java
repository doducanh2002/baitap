package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class UserActivatedException extends BadRequestException {
  public UserActivatedException(String email) {
    setCode("org.up.finance.exception.UserActivatedException");
    addParam("email", email);
  }
}

package org.up.finance.exception;

import org.up.finance.exception.base.ConflictException;

public class UsernameAlreadyExistException extends ConflictException {
  public UsernameAlreadyExistException(String username) {
    addParam("username", username);
    setCode("org.up.finance.exception.UsernameAlreadyExistException");
  }
}

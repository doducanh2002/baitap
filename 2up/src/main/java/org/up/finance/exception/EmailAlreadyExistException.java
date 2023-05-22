package org.up.finance.exception;

import org.up.finance.exception.base.ConflictException;

public class EmailAlreadyExistException extends ConflictException {
  public EmailAlreadyExistException(String email) {
    addParam("email", email);
    setCode("org.up.finance.exception.EmailAlreadyExistException");
  }
}

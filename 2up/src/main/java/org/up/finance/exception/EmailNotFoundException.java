package org.up.finance.exception;

import org.up.finance.exception.base.NotFoundException;

public class EmailNotFoundException extends NotFoundException {

  public EmailNotFoundException(String email) {
    setCode("org.up.finance.exception.EmailNotFoundException");
    addParam("email", email);
  }
}

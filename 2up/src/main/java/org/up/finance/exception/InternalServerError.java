package org.up.finance.exception;

import org.up.finance.exception.base.BaseException;

public class InternalServerError extends BaseException {

  public InternalServerError() {
    setStatus(500);
    setCode("org.up.finance.exception.InternalServerError");
  }

  public InternalServerError(String message) {
    setStatus(500);
    setMessage(message);
  }
}

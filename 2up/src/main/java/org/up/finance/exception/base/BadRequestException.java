package org.up.finance.exception.base;

public class BadRequestException extends BaseException{
  public BadRequestException() {
    setCode("org.up.finance.exception.base.BadRequestException");
    setStatus(400);
  }
}

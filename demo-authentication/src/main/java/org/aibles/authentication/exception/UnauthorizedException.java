package org.aibles.authentication.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException{

  public UnauthorizedException() {
    super();
    setCode("org.aibles.interceptordemo.exception.UnauthorizedException");
    setStatus(HttpStatus.UNAUTHORIZED.value());
  }
}

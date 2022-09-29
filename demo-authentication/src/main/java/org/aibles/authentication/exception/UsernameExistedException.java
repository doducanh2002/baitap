package org.aibles.authentication.exception;

import org.springframework.http.HttpStatus;

public class UsernameExistedException extends BaseException {

  public UsernameExistedException(String username) {
    super();
    setCode("Tài khoản đã tồn tại");
    setStatus(HttpStatus.BAD_REQUEST.value());
    addParams("username", username);
  }
}

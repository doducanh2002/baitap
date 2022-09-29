package org.aibles.authentication.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{

  public BadRequestException(Object field, Object type) {
    super();
    setCode("Không thành công");
    setStatus(HttpStatus.BAD_REQUEST.value());
    addParams("field", field);
    addParams("type", type);
  }
}

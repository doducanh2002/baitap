package org.up.finance.exception.base;

import static org.up.finance.exception.base.StatusConstants.CONFLICT;

public class ConflictException extends BaseException {
  public ConflictException(String id, String objectName) {
    setStatus(CONFLICT);
    setCode("org.up.finance.exception.base.ConflictException");
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public ConflictException(){
    setStatus(CONFLICT);
    setCode("org.up.finance.exception.base.ConflictException");
  }
}

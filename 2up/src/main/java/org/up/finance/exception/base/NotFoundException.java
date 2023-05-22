package org.up.finance.exception.base;

import static org.up.finance.exception.base.StatusConstants.NOT_FOUND;

public class NotFoundException extends BaseException {
  public NotFoundException(String id, String objectName) {
    setCode("org.up.finance.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public NotFoundException(String id){
    addParam("id", id);
    setCode("org.up.finance.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
  }

  public NotFoundException() {
    setCode("org.up.finance.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
  }
}

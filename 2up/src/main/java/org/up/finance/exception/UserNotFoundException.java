package org.up.finance.exception;

import org.up.finance.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(String id){
    addParam("id", id);
    setCode("org.up.finance.exception.UserNotFoundException");
  }

}

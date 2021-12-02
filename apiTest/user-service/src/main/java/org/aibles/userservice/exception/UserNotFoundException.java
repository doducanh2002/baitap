package org.aibles.userservice.exception;

import org.aibles.userservice.exception.common.UserServiceError;
import org.aibles.userservice.exception.common.UserServiceException;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException() {
        super(UserServiceError.USER_NOT_FOUND);
    }
}

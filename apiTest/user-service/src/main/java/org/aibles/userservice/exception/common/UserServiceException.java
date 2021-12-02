package org.aibles.userservice.exception.common;

public abstract class UserServiceException extends RuntimeException{

    private final UserServiceError userServiceError;

    private static final long serialVersionUID = 1L;

    protected UserServiceException(UserServiceError userServiceError){
        super();
        this.userServiceError = userServiceError;
    }

    public UserServiceError getUserServiceError() {
        return userServiceError;
    }
}

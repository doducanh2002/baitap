package org.squad3.library.user.exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1l;

    public UserNotFoundException() {
        super(UserExceptionMessage.USER_NOT_FOUND_EXCEPTION);
    }
}

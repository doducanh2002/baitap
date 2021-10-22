package org.squad3.library.user.exceptions;

public class InvalidRoleException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public InvalidRoleException() {
        super(UserExceptionMessage.INVALID_ROLE_EXCEPTION);
    }
}

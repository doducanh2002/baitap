package org.squad3.library.user.exceptions;

public class UsernameExistedException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public UsernameExistedException() {
        super(UserExceptionMessage.USERNAME_EXISTED_EXCEPTION);
    }
}

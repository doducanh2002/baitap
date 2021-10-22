package org.squad3.library.user.exceptions;

public class EmailExistedException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    public EmailExistedException() {
        super(UserExceptionMessage.EMAIL_EXISTED_EXCEPTION);
    }
}

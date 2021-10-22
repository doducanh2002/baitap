package org.squad3.library.user.exceptions;

import lombok.Data;

@Data
public class UserExceptionMessage {
    public static final String USER_NOT_FOUND_EXCEPTION = "User not found exception.";
    public static final String INVALID_ROLE_EXCEPTION = "Invalid role exception.";
    public static final String USERNAME_EXISTED_EXCEPTION = "Username existed exception.";
    public static final String EMAIL_EXISTED_EXCEPTION = "Email existed exception";
}

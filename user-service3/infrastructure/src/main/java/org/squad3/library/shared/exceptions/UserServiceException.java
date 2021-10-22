package org.squad3.library.shared.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class UserServiceException extends RuntimeException{

    private String message;
    private final HttpStatus httpStatus;

    public UserServiceException(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

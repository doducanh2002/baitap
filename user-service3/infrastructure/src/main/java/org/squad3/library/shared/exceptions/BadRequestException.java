package org.squad3.library.shared.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends UserServiceException{

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

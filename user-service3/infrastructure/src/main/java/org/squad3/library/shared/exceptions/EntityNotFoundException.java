package org.squad3.library.shared.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends UserServiceException{
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

package org.squad3.library.shared.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends UserServiceException{
    public EntityNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

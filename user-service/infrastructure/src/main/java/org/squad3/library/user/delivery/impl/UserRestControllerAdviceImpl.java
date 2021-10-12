package org.squad3.library.user.delivery.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.squad3.library.shared.constants.CommonConstants;
import org.squad3.library.shared.exception.EntityNotFound;
import org.squad3.library.user.delivery.UserRestControllerAdvice;
import org.squad3.library.user.delivery.responses.SystemResponse;

@RestControllerAdvice
public class UserRestControllerAdviceImpl implements UserRestControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFound.class)
    @Override
    public SystemResponse<String> handleEntityNotFoundException(EntityNotFound ex) {
        return SystemResponse.<String>builder()
                .status(CommonConstants.ERROR)
                .code(String.valueOf(ex.getHttpStatus().value()))
                .message(CommonConstants.NOT_FOUND)
                .data(ex.getMessage())
                .build();
    }
}

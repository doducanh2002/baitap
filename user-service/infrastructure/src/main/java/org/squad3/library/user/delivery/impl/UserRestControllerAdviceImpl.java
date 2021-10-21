package org.squad3.library.user.delivery.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.squad3.library.shared.constants.CommonConstants;
import org.squad3.library.shared.exceptions.BadRequestException;
import org.squad3.library.shared.exceptions.EntityNotFoundException;
import org.squad3.library.shared.exceptions.UserServiceException;
import org.squad3.library.user.delivery.UserRestControllerAdvice;
import org.squad3.library.user.delivery.responses.SystemResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class UserRestControllerAdviceImpl implements UserRestControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    @Override
    public SystemResponse<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return SystemResponse.<String>builder()
                .status(CommonConstants.ERROR)
                .code(String.valueOf(ex.getHttpStatus().value()))
                .message(CommonConstants.NOT_FOUND)
                .data(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ConstraintViolationException.class })
    @Override
    public SystemResponse<Object> validateErrorHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
        List<String> errorMessages = new ArrayList<>();
        for (Iterator<ConstraintViolation<?>> iterator = set.iterator(); iterator.hasNext();) {
            ConstraintViolation<?> next = iterator.next();
            errorMessages.add(next.getMessage());
        }
        return SystemResponse.builder()
                .status(CommonConstants.ERROR)
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(CommonConstants.BAD_REQUEST)
                .data(errorMessages)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    @Override
    public SystemResponse<String> handleBadRequestException(BadRequestException ex) {
        return SystemResponse.<String>builder()
                .status(CommonConstants.ERROR)
                .code(String.valueOf(ex.getHttpStatus().value()))
                .message(CommonConstants.BAD_REQUEST)
                .data(ex.getMessage())
                .build();
    }
}
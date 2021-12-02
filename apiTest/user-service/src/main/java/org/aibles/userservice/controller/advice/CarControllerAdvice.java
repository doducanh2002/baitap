package org.aibles.userservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.aibles.userservice.exception.common.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CarControllerAdvice {

    @ExceptionHandler(value = UserServiceException.class)
    public ResponseEntity<String> UserServiceExceptionHandle(UserServiceException e){
        return new ResponseEntity<>(
                e.getUserServiceError().getErrorMessage(),
                HttpStatus.valueOf(e.getUserServiceError().getErrorCode())
        );
    }
}

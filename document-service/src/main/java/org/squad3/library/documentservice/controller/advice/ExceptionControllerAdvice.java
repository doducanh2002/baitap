package org.squad3.library.documentservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.squad3.library.documentservice.exception.common.CategoryException;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = CategoryException.class)
    public ResponseEntity<String> CategoryServiceExceptionHandle(CategoryException e) {
        return new ResponseEntity<>(
                e.getServiceError().getErrorMessage(),
                HttpStatus.valueOf(e.getServiceError().getErrorCode())
        );
    }

}

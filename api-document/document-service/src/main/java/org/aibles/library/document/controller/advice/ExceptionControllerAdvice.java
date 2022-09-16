package org.aibles.library.document.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.aibles.library.document.exception.common.CategoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

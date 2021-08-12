package com.example.restfullapi.controller.advice;

import com.example.restfullapi.exception.common.TeacherServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TeacherControllerAdvice {

    @ExceptionHandler(value = TeacherServiceException.class)
    public ResponseEntity<String> TeacherServiceExceptionHandle(TeacherServiceException e){
        return new ResponseEntity<>(
                e.getTeacherServiceError().getErrorMessage(),
                HttpStatus.valueOf(e.getTeacherServiceError().getErrorCode())
        );
    }
}

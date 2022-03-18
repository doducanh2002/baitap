package org.squad3.library.documentservice.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.squad3.library.documentservice.exception.common.DocumentServiceException;

@Slf4j
@RestControllerAdvice
public class DocumentControllerAdvice {

    @ExceptionHandler(value = DocumentServiceException.class)
    public ResponseEntity<String> DocumentServiceExceptionHandle(DocumentServiceException e){
        return new ResponseEntity<>(
                e.getDocumentServiceError().getErrorMessage(),
                HttpStatus.valueOf(e.getDocumentServiceError().getErrorCode())
        );
    }

}


package org.aibles.library.document.exception.common;

import lombok.Getter;

@Getter
public enum DocumentServiceError {

    DOCUMENT_NOT_FOUND(404,"Document không tồn tại" );
    //defind exception here

    private final int errorCode;
    private final String errorMessage;

    private DocumentServiceError(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage= errorMessage;
    }
}

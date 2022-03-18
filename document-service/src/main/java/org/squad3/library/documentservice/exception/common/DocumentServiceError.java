package org.squad3.library.documentservice.exception.common;

import lombok.Getter;

@Getter
public enum DocumentServiceError {

    DOCUMENT_NOT_FOUND(404,"DOCUMENT_NOT_FOUND" );
    //defind exception here

    private final int errorCode;
    private final String errorMessage;

    private DocumentServiceError(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage= errorMessage;
    }
}

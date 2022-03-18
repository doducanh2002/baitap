package org.squad3.library.documentservice.exception.common;

import lombok.Getter;

@Getter
public abstract class DocumentServiceException extends RuntimeException{

    private final DocumentServiceError documentServiceError;

    private static final long serialVersionUID = 1L;

    protected DocumentServiceException(DocumentServiceError documentServiceError){
        super();
        this.documentServiceError = documentServiceError;
    }

    public DocumentServiceError getDocumentServiceError(){
        return documentServiceError;
    }

}

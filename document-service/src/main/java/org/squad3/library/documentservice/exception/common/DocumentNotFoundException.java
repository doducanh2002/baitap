package org.squad3.library.documentservice.exception.common;

public class DocumentNotFoundException extends DocumentServiceException{
    public DocumentNotFoundException(){super(DocumentServiceError.DOCUMENT_NOT_FOUND);}
}

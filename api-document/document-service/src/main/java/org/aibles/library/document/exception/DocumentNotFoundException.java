package org.aibles.library.document.exception;

import org.aibles.library.document.exception.common.DocumentServiceError;
import org.aibles.library.document.exception.common.DocumentServiceException;

public class DocumentNotFoundException extends DocumentServiceException {
    public DocumentNotFoundException(){super(DocumentServiceError.DOCUMENT_NOT_FOUND);}
}

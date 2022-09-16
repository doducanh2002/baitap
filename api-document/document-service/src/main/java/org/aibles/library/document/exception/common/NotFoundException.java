package org.aibles.library.document.exception.common;

public class NotFoundException extends RunException {

    public NotFoundException() {
        setStatus(404);
        setMessage("org.aibles.library.documentservice.exception.common.NotFoundException");
    }
}

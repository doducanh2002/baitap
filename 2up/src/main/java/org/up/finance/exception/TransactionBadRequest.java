package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class TransactionBadRequest extends BadRequestException {

    public TransactionBadRequest(String id) {
        addParam("id", id);
        setCode("org.up.finance.exception.CategoryBadRequestException");
    }
}
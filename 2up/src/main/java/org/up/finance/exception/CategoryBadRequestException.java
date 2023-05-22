package org.up.finance.exception;

import org.up.finance.exception.base.BadRequestException;

public class CategoryBadRequestException extends BadRequestException {

    public CategoryBadRequestException(String id) {
        addParam("id", id);
        setCode("org.up.finance.exception.CategoryBadRequestException");
    }
}

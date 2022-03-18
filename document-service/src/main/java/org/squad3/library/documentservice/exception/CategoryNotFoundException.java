package org.squad3.library.documentservice.exception;

import org.squad3.library.documentservice.exception.common.CategoryError;
import org.squad3.library.documentservice.exception.common.CategoryException;

public class CategoryNotFoundException extends CategoryException {
    public CategoryNotFoundException() {
        super(CategoryError.CATEGORY_NOT_FOUND);
    }
}

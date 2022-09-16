package org.aibles.library.document.exception;

import org.aibles.library.document.exception.common.CategoryError;
import org.aibles.library.document.exception.common.CategoryException;

public class CategoryNotFoundException extends CategoryException {
    public CategoryNotFoundException() {
        super(CategoryError.CATEGORY_NOT_FOUND);
    }
}

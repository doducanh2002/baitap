package org.squad3.library.documentservice.exception.common;

public class CategoryException extends RuntimeException{

    final private CategoryError categoryError;

    protected CategoryException(CategoryError categoryError) {
        super();
        this.categoryError = categoryError;
    }

    public CategoryError getServiceError() {
        return categoryError;
    }
}

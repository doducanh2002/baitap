package org.aibles.library.document.exception.common;

import lombok.Getter;

@Getter
public enum CategoryError {

    CATEGORY_NOT_FOUND(404, "Category not found");

    private final int errorCode;
    private final String errorMessage;

    private CategoryError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

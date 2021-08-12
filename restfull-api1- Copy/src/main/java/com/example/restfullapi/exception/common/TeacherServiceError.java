package com.example.restfullapi.exception.common;

public enum TeacherServiceError {

    TEACHER_NOT_FOUND(404,"TEACHER NOT FOUND"),
    GONE(410,"GONE"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451,"UNAVAILABLE_FOR_LEGAL_REASONS");


    private TeacherServiceError(int errorCode, String errMessage){
        this.errorCode = errorCode;
        this.errorMessage = errMessage;
    }

    private final int errorCode;
    private final String errorMessage;

    public int getErrorCode() { return errorCode; }

    public String getErrorMessage() { return errorMessage; }

}

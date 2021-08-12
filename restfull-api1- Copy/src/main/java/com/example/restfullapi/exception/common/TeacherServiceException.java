package com.example.restfullapi.exception.common;

public abstract class TeacherServiceException extends RuntimeException {

    private final TeacherServiceError teacherServiceError ;

    private static final long serialVersionUID = 1L;

    protected TeacherServiceException(TeacherServiceError teacherServiceError){
        super();
        this.teacherServiceError =teacherServiceError;
    }

    public TeacherServiceError getTeacherServiceError(){
        return teacherServiceError;
    }
}

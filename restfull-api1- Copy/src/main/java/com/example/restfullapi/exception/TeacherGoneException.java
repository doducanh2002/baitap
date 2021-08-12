package com.example.restfullapi.exception;

import com.example.restfullapi.exception.common.TeacherServiceError;
import com.example.restfullapi.exception.common.TeacherServiceException;

public class TeacherGoneException extends TeacherServiceException {
    public TeacherGoneException(){super(TeacherServiceError.GONE);}
}

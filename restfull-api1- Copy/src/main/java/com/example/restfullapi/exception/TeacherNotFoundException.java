package com.example.restfullapi.exception;

import com.example.restfullapi.exception.common.TeacherServiceError;
import com.example.restfullapi.exception.common.TeacherServiceException;

public class TeacherNotFoundException extends TeacherServiceException {
        public TeacherNotFoundException(){super(TeacherServiceError.TEACHER_NOT_FOUND);}

}

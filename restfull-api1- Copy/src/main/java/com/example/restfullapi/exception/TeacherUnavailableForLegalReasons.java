package com.example.restfullapi.exception;

import com.example.restfullapi.exception.common.TeacherServiceError;
import com.example.restfullapi.exception.common.TeacherServiceException;

public class TeacherUnavailableForLegalReasons extends TeacherServiceException {
    public TeacherUnavailableForLegalReasons(){super(TeacherServiceError.UNAVAILABLE_FOR_LEGAL_REASONS);}
}

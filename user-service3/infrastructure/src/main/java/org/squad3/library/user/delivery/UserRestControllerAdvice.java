package org.squad3.library.user.delivery;

import org.squad3.library.shared.exceptions.BadRequestException;
import org.squad3.library.shared.exceptions.EntityNotFoundException;
import org.squad3.library.shared.exceptions.UserServiceException;
import org.squad3.library.user.delivery.responses.SystemResponse;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface UserRestControllerAdvice {
    SystemResponse<String> handleEntityNotFoundException(EntityNotFoundException ex);
    SystemResponse<Object> validateErrorHandler(ConstraintViolationException ex);
    SystemResponse<String> handleBadRequestException(BadRequestException ex);
}

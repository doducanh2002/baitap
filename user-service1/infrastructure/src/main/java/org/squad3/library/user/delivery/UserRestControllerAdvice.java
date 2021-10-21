package org.squad3.library.user.delivery;

import org.squad3.library.shared.exception.EntityNotFound;
import org.squad3.library.user.delivery.responses.SystemResponse;

public interface UserRestControllerAdvice {
    SystemResponse<String> handleEntityNotFoundException (EntityNotFound ex);
}

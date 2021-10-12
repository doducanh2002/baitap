package org.squad3.library.user.delivery;

import org.squad3.library.user.delivery.responses.SystemResponse;
import org.squad3.library.user.delivery.rest.dto.UserDTO;

public interface UserRestController {
    SystemResponse<UserDTO> getUserName(String username);
}

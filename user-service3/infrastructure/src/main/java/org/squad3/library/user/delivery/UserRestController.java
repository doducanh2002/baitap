package org.squad3.library.user.delivery;

import org.squad3.library.user.delivery.responses.SystemResponse;
import org.squad3.library.user.delivery.rest.dto.UserDTO;

public interface UserRestController {
    SystemResponse<UserDTO> createUser(final UserDTO user);
    SystemResponse<UserDTO> getUserByUsername(final String email, String name );
    SystemResponse<UserDTO> getUserByEmail(final String email, String name );

}

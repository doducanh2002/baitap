package org.squad3.library.user.delivery.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.shared.constants.CommonConstants;
import org.squad3.library.shared.exception.EntityNotFound;
import org.squad3.library.user.User;
import org.squad3.library.user.delivery.UserRestController;
import org.squad3.library.user.delivery.responses.SystemResponse;
import org.squad3.library.user.delivery.rest.dto.AccountDTO;
import org.squad3.library.user.delivery.rest.dto.UserDTO;
import org.squad3.library.user.exception.UserNotFoundException;
import org.squad3.library.user.usecase.GetUserNameUseCase;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserRestControllerImpl implements UserRestController {

    private final GetUserNameUseCase getUserNameUseCase;

    private final RestDTOConverter<UserDTO, User> userRestDTOConverter;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/*/accounts", params = "username")
    public SystemResponse<UserDTO> getUserName(@RequestParam("username") String username) {
        User user;
        try {
            user = getUserNameUseCase.execute(username);
        } catch (UserNotFoundException ex){
            throw new EntityNotFound(ex.getMessage());
        }
        return SystemResponse.<UserDTO>builder()
                .status(CommonConstants.SUCCESS)
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(CommonConstants.OK)
                .data(userRestDTOConverter.mapToDTO(user))
                .build();
    }
}

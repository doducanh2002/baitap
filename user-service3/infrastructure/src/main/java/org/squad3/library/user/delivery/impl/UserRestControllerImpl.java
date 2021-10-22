package org.squad3.library.user.delivery.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.shared.constants.CommonConstants;
import org.squad3.library.shared.constants.RestConstants;
import org.squad3.library.shared.exceptions.BadRequestException;
import org.squad3.library.shared.exceptions.EntityNotFoundException;
import org.squad3.library.user.User;
import org.squad3.library.user.delivery.UserRestController;
import org.squad3.library.user.delivery.responses.SystemResponse;
import org.squad3.library.user.delivery.rest.dto.UserDTO;
import org.squad3.library.user.exceptions.EmailExistedException;
import org.squad3.library.user.exceptions.InvalidRoleException;
import org.squad3.library.user.exceptions.UserNotFoundException;
import org.squad3.library.user.exceptions.UsernameExistedException;
import org.squad3.library.user.usecase.CreateUserUseCase;
import org.squad3.library.user.usecase.GetUserByUsernameOrGmailUseCase;

@RequiredArgsConstructor
@RestController
@RequestMapping(RestConstants.API_PATH + RestConstants.API_VERSION_1 + RestConstants.RESOURCE_USER)
public class UserRestControllerImpl implements UserRestController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByUsernameOrGmailUseCase getUserByUsernameOrGmailUseCase;
    private final RestDTOConverter<UserDTO, User> userRestDTOConverter;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public SystemResponse<UserDTO> createUser(@Validated() @RequestBody UserDTO userDTO) {
        User user;
        try {
            user = createUserUseCase.execute(userRestDTOConverter.mapToEntity(userDTO));
        } catch (EmailExistedException ex){
            throw new BadRequestException(ex.getMessage());

        } catch (UsernameExistedException ex){
            throw new BadRequestException(ex.getMessage());

        } catch (InvalidRoleException ex){
            throw new BadRequestException(ex.getMessage());
        }

        return SystemResponse.<UserDTO>builder()
                .status(CommonConstants.SUCCESS)
                .code(String.valueOf(HttpStatus.CREATED.value()))
                .message(CommonConstants.OK)
                .data(userRestDTOConverter.mapToDTO(user))
                .build();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/accounts", params = {"email"})
    public SystemResponse<UserDTO> getUserByEmail(@RequestParam( "email") String name , String email) {
        User user;
        try {
            user = getUserByUsernameOrGmailUseCase.execute(name,email);
        } catch (UserNotFoundException ex){
            throw new EntityNotFoundException(ex.getMessage());
        }
        return SystemResponse.<UserDTO>builder()
                .status(CommonConstants.SUCCESS)
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(CommonConstants.OK)
                .data(userRestDTOConverter.mapToDTO(user))
                .build();
    }
    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/accounts", params = {"username"})
    public SystemResponse<UserDTO> getUserByUsername(@RequestParam( "username") String name , String email) {
        User user;
        try {
            user = getUserByUsernameOrGmailUseCase.execute(name,email);
        } catch (UserNotFoundException ex){
            throw new EntityNotFoundException(ex.getMessage());
        }
        return SystemResponse.<UserDTO>builder()
                .status(CommonConstants.SUCCESS)
                .code(String.valueOf(HttpStatus.OK.value()))
                .message(CommonConstants.OK)
                .data(userRestDTOConverter.mapToDTO(user))
                .build();
    }
}

package org.squad3.library.user.delivery.converters;

import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.Role;
import org.squad3.library.user.User;
import org.squad3.library.user.delivery.rest.dto.UserDTO;

import java.util.Optional;

public class UserRestDTOConverter implements RestDTOConverter<UserDTO, User> {

    private AccountRestDTOConverter accountRestDTOConverter;
    @Override
    public UserDTO mapToDTO(User user) {
        //TODO: convert user to DTO
        return Optional.ofNullable(user)
                .map(u -> {
                    return UserDTO.builder()
                            .name(u.getName())
                            .email(u.getEmail())
                            .phone(u.getPhone())
                            .account(accountRestDTOConverter.mapToDTO(u.getAccount()))
                            .role(u.getRole().getRoleName())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public User mapToEntity(UserDTO reqDTO) {
        //TODO: convert dto to entity
        final Role role = Role.builder().roleName(reqDTO.getRole()).build();
        final Account account = accountRestDTOConverter.mapToEntity(reqDTO.getAccount());
        return User.builder()
                .name(reqDTO.getName())
                .email(reqDTO.getEmail())
                .phone(reqDTO.getPhone())
                .account(account)
                .role(role)
                .build();
    }
}

package org.squad3.library.user.delivery.converters;

import lombok.AllArgsConstructor;
import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.Role;
import org.squad3.library.user.User;
import org.squad3.library.user.delivery.rest.dto.UserDTO;

import java.util.Optional;

@AllArgsConstructor
public class UserRestDTOConverter implements RestDTOConverter<UserDTO, User> {

    private final AccountRestDTOConverter accountRestDTOConverter;

    @Override
    public UserDTO mapToDTO(User user) {
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

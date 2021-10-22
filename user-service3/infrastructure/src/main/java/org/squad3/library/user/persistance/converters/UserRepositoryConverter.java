package org.squad3.library.user.persistance.converters;

import lombok.AllArgsConstructor;
import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.Role;
import org.squad3.library.user.User;
import org.squad3.library.user.persistance.entites.AccountEntity;
import org.squad3.library.user.persistance.entites.RoleEntity;
import org.squad3.library.user.persistance.entites.UserEntity;
import org.squad3.library.user.persistance.entites.UserRoleEntity;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class UserRepositoryConverter implements RepositoryConverter<UserEntity, User> {

    private final RepositoryConverter<AccountEntity, Account> accountRepositoryConverter;
    private final RepositoryConverter<RoleEntity, Role> roleRepositoryConverter;

    @Override
    public UserEntity mapToTable(User userPersistence) {
        return Optional.ofNullable(userPersistence)
                .map(up -> {
                    final UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                            .roleEntity(roleRepositoryConverter.mapToTable(up.getRole()))
                            .build();
                    UserEntity userEntity = UserEntity.builder()
                            .name(up.getName())
                            .email(up.getEmail())
                            .phone(up.getPhone())
                            .accountEntity(accountRepositoryConverter.mapToTable(up.getAccount()))
                            .userRoleEntity(userRoleEntity)
                            .build();
                    userEntity.getAccountEntity().setUserEntity(userEntity);
                    userEntity.getUserRoleEntity().setUserEntity(userEntity);
                    return userEntity;
                })
                .orElse(null);
    }

    @Override
    public User mapToEntity(UserEntity userTable) {
        return Optional.ofNullable(userTable)
                .map(ut -> {
                    return User.builder()
                            .id(ut.getId())
                            .name(ut.getName())
                            .email(ut.getEmail())
                            .phone(ut.getPhone())
                            .account(accountRepositoryConverter.mapToEntity(ut.getAccountEntity()))
                            .role(roleRepositoryConverter.mapToEntity(ut.getUserRoleEntity().getRoleEntity()))
                            .build();
                })
                .orElse(null);
    }
}

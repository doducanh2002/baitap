package org.squad3.library.user.usecase.impl;

import lombok.AllArgsConstructor;
import org.squad3.library.user.Account;
import org.squad3.library.user.Role;
import org.squad3.library.user.User;
import org.squad3.library.user.exceptions.EmailExistedException;
import org.squad3.library.user.exceptions.InvalidRoleException;
import org.squad3.library.user.exceptions.UsernameExistedException;
import org.squad3.library.user.ports.AccountRepositoryService;
import org.squad3.library.user.ports.RoleRepositoryService;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.CreateUserUseCase;

@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepositoryService userRepositoryService;
    private final RoleRepositoryService roleRepositoryService;
    private final AccountRepositoryService accountRepositoryService;

    @Override
    public User execute(User user){
        user.validateSelf();
        validateEmail(user.getEmail());
        validateAccountUsername(user.getAccount());
        user.setRole(this.getRoleByRoleName(user.getRole().getRoleName()));
        return userRepositoryService.saveUser(user);
    }

    private void validateEmail(String email){
        if (userRepositoryService.isEmailExisted(email)){
            throw new EmailExistedException();
        }
    }

    private void validateAccountUsername(Account account){
        if (accountRepositoryService.isUsernameExisted(account.getUsername())){
            throw new UsernameExistedException();
        }
    }

    private Role getRoleByRoleName(String role){
        return roleRepositoryService.getRoleByRoleName(role)
                .orElseThrow(InvalidRoleException::new);
    }
}

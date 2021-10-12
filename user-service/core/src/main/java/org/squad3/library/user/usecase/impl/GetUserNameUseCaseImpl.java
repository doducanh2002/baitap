package org.squad3.library.user.usecase.impl;

import lombok.AllArgsConstructor;
import org.squad3.library.user.Account;
import org.squad3.library.user.User;
import org.squad3.library.user.exception.UserNotFoundException;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.GetUserNameUseCase;

@AllArgsConstructor
public class GetUserNameUseCaseImpl implements GetUserNameUseCase {

    private final UserRepositoryService userRepositoryService;

    @Override
    public User execute(String username) {
        return userRepositoryService.getUserName(username).orElseThrow(UserNotFoundException::new);}
}

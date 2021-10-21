package org.squad3.library.user.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.squad3.library.user.User;
import org.squad3.library.user.exceptions.UserNotFoundException;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.GetUserByUsernameUseCase;

@RequiredArgsConstructor
public class GetUserByUsernameUseCaseImpl implements GetUserByUsernameUseCase {

    private final UserRepositoryService userRepositoryService;

    @Override
    public User execute(String username) {
        return userRepositoryService.getUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}

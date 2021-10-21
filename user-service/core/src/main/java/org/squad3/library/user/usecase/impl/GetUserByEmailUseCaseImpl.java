package org.squad3.library.user.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.squad3.library.user.User;
import org.squad3.library.user.exceptions.UserNotFoundException;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.GetUserByEmailUseCase;

@RequiredArgsConstructor
public class GetUserByEmailUseCaseImpl implements GetUserByEmailUseCase {

    private final UserRepositoryService userRepositoryService;

    @Override
    public User execute(String email) {
        return userRepositoryService.getUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}

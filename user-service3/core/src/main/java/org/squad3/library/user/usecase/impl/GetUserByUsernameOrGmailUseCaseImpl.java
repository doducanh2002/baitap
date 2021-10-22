package org.squad3.library.user.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.squad3.library.user.User;
import org.squad3.library.user.exceptions.UserNotFoundException;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.GetUserByUsernameOrGmailUseCase;

@RequiredArgsConstructor
public class GetUserByUsernameOrGmailUseCaseImpl implements GetUserByUsernameOrGmailUseCase {

    private final UserRepositoryService userRepositoryService;

    @Override
    public User execute(String username, String email) {
        return userRepositoryService.getUserByUsernameOrEmail(username, email)
                .orElseThrow(UserNotFoundException::new);
    }

}

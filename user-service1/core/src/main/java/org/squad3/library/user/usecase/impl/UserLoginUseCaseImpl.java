package org.squad3.library.user.usecase.impl;

import lombok.AllArgsConstructor;
import org.squad3.library.user.ports.UserEventGateway;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.UserLoginUseCase;

@AllArgsConstructor
public class UserLoginUseCaseImpl implements UserLoginUseCase {

    private final UserRepositoryService userRepositoryService;
    private final UserEventGateway userEventGateway;
}

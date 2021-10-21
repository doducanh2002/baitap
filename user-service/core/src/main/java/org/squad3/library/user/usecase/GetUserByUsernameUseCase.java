package org.squad3.library.user.usecase;

import org.squad3.library.user.User;

public interface GetUserByUsernameUseCase {
    User execute(String username);
}

package org.squad3.library.user.usecase;

import org.squad3.library.user.User;

public interface GetUserByUsernameOrGmailUseCase {
    User execute(String username ,String email);

}

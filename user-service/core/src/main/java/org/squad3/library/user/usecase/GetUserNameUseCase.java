package org.squad3.library.user.usecase;

import org.squad3.library.user.Account;
import org.squad3.library.user.User;

public interface GetUserNameUseCase {
    User execute (String username);
}

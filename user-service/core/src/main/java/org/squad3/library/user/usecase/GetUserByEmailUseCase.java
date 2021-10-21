package org.squad3.library.user.usecase;

import org.squad3.library.user.User;

public interface GetUserByEmailUseCase{
    User execute(String email);

}

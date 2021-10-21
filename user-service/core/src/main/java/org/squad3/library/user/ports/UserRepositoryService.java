package org.squad3.library.user.ports;

import org.squad3.library.user.User;

import java.util.Optional;

/**
 * This interface where will receive request from gateway(delivery: api)
 */
public interface UserRepositoryService {
    User saveUser (User user);
    boolean isEmailExisted(String email);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
}

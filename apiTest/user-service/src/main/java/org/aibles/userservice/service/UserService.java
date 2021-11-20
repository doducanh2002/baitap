package org.aibles.userservice.service;

import org.aibles.userservice.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(int userId, User user);

    void deleteUser(int teacherId);

    List<User> listUsers();

}

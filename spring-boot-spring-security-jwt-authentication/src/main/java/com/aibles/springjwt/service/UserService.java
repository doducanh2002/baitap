package com.aibles.springjwt.service;

import com.aibles.springjwt.data.models.User;

public interface UserService {
    User updateUser(int userId, User user);

    User deleteUser(int userId);



}



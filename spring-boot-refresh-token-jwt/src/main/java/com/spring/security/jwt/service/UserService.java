package com.spring.security.jwt.service;


import com.spring.security.jwt.data.models.User;

public interface UserService {
    User updateUser(long userId, User user);

    User deleteUser(long userId);



}



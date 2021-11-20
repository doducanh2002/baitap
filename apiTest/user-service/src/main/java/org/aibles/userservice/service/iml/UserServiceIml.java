package org.aibles.userservice.service.iml;


import org.aibles.userservice.model.User;
import org.aibles.userservice.repository.UserRepository;
import org.aibles.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserServiceIml implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceIml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        User userCreated  = userRepository.save(user);
        return userCreated;
    }

    @Override
    public User updateUser(int userId, User user) {
        User result = userRepository.findById(userId)
                .map(userNew -> {
                    user.setName(user.getName());
                    user.setAge(user.getAge());
                    return user;
                })
                .map(userRepository::save)
                .orElse(null);
        return result;
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                }).orElse(null);

    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

}


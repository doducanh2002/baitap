package org.aibles.userservice.service.iml;

    import org.aibles.userservice.exception2.UserNotFoundException;
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
    public User updateUser(int userId, User userReq) {
        User result = userRepository.findById(userId)
                .map(user -> {
                    user.setName(userReq.getName());
                    user.setAge(userReq.getAge());
                    return user;
                })
                .map(userRepository::save)
                .orElseThrow(UserNotFoundException::new);
        return result;
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                }).orElseThrow(UserNotFoundException::new);

    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}


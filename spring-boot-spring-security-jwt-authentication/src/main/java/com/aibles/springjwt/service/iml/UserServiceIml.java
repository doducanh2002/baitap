package com.aibles.springjwt.service.iml;


import com.aibles.springjwt.data.models.User;
import com.aibles.springjwt.service.UserService;
import com.aibles.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIml implements UserService {

    @Autowired
    private final UserRepository userRepository;


    public UserServiceIml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User updateUser(int userId, User userReq) {
            User user1 = userRepository.findById(userId)
                    .map(result -> {

                        result.setPassword(userReq.getPassword());
                        result.setEmail(userReq.getEmail());

                        return result;
                    })
                    .map(userRepository::save)
                    .orElse(null);
            return user1;
        }


    @Override
    public User deleteUser(int userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                })
                .orElse(null);
    }

}


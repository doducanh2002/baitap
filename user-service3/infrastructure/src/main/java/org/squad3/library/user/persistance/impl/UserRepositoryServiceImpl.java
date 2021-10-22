package org.squad3.library.user.persistance.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.User;
import org.squad3.library.user.persistance.entites.UserEntity;
import org.squad3.library.user.persistance.repositories.UserRepository;
import org.squad3.library.user.ports.UserRepositoryService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;
    private final RepositoryConverter<UserEntity, User> userRepositoryConverter;

    @Override
    @CachePut(value = "users")
    public User saveUser(User user) {
        final UserEntity userEntity = userRepositoryConverter.mapToTable(user);
        return userRepositoryConverter.mapToEntity(userRepository.save(userEntity));
    }

    @Override
    public boolean isEmailExisted(String email) {
        return userRepository.existsUserEntityByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsernameOrEmail(String email,String username) {
        final UserEntity userEntity = userRepository.findByAccountEntityUsernameOrEmail(email,username)
                .orElse(null);
        return Optional.ofNullable(userRepositoryConverter.mapToEntity(userEntity));
    }


}
package org.squad3.library.user.persistance.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.User;
import org.squad3.library.user.persistance.converters.AccountRepositoryConverter;
import org.squad3.library.user.persistance.entites.UserEntity;
import org.squad3.library.user.persistance.repositories.AccountRepository;
import org.squad3.library.user.persistance.repositories.UserRepository;
import org.squad3.library.user.ports.UserRepositoryService;

import java.util.Optional;

@AllArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;
    private final RepositoryConverter<UserEntity, User> userRepositoryConverter;

    @Override
    public Optional<User> getUserName(String username) {
        final UserEntity userEntity = userRepository.findByAccountEntityUsername(username)
                .orElse(null);
        return Optional.ofNullable(userRepositoryConverter.mapToEntity(userEntity));
    }
}

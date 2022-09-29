package org.aibles.authentication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.authentication.converter.MappingHelper;
import org.aibles.authentication.dto.reponse.UserResponseDTO;
import org.aibles.authentication.dto.request.LoginRequest;
import org.aibles.authentication.dto.request.SignUpRequest;
import org.aibles.authentication.entity.User;
import org.aibles.authentication.exception.BadRequestException;
import org.aibles.authentication.exception.NotFoundException;
import org.aibles.authentication.exception.UsernameExistedException;
import org.aibles.authentication.repository.UserRepository;
import org.aibles.authentication.service.UserService;
import org.aibles.authentication.utils.BasicAuthUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private static final String TYPE_OF_OBJECT = "user";
  private final UserRepository repository;
  private final MappingHelper mappingHelper;

  @Override
  public UserResponseDTO login(LoginRequest request) {
    log.info("(login)username : {}", request.getUsername());
    if (!repository.existsByUsernameAndPassword(request.getUsername(), request.getPassword())) {
      throw new BadRequestException(request.getUsername(), TYPE_OF_OBJECT);
    }
    User user =
            repository
                    .findByUsername(request.getUsername())
                    .orElseThrow(() -> new NotFoundException(request.getUsername(), request.getPassword()));
    return mappingHelper.map(repository.save(user), UserResponseDTO.class);
  }

  @Override
  public void register(SignUpRequest request) {
    log.info("(register)username : {}", request.getUsername());
    if (repository.existsByUsername(request.getUsername())) {
      throw new UsernameExistedException(request.getUsername());
    }
    User user = request.toUser();
    repository.save(user);
  }

}
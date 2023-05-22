package org.up.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.up.finance.entity.User;
import org.up.finance.exception.EmailAlreadyExistException;
import org.up.finance.exception.EmailNotFoundException;
import org.up.finance.exception.UserNotFoundException;
import org.up.finance.exception.UsernameAlreadyExistException;
import org.up.finance.repository.UserRepository;
import org.up.finance.service.UserService;

@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public void active(String email) {
    log.info("(active) email: {}", email);
    repository.activeUser(email);
  }

  @Override
  public boolean checkActivated(String email) {
    log.info("(checkActivated) email: {}", email);
    return repository.isActivated(email);
  }

  @Transactional
  public User create(String username, String password, String email, String fullName) {
    log.info("(create) username:{}, email:{}, fullName:{}", username, email, fullName);

    this.checkExist(username, email);
    return repository.save(User.of(username, password, email, fullName));
  }

  public boolean existsByEmail(String email) {
    log.info("(existsByEmail) email: {}", email);
    if (!repository.existsByEmail(email)) {
      log.error("(existsByEmail) existsByEmail:{} -> EmailNotFoundException", email);
      throw new EmailNotFoundException(email);
    }
    return true;
  }

  public User findByEmail(String email) {
    log.info("(findByEmail) email: {}", email);
    return repository.findByEmail(email).orElseThrow(() -> {
      log.error("(findByEmail) email: {} -> EmailNotFoundException", email);
      throw new EmailNotFoundException(email);
    });
  }

  public User get(String id) {
    log.info("(get) id: {}", id);
    return repository.findById(id).orElseThrow(() -> {
      log.error("(get) id: {} -> UserNotFoundException", id);
      throw new UserNotFoundException(id);
    });
  }

  private void checkExist(String username, String email) {
    if (repository.existsByEmail(email)) {
      log.error("(create) email:{} -> EmailAlreadyExistException", email);
      throw new EmailAlreadyExistException(email);
    }
    if (repository.existsByUsername(username)) {
      log.error("(create) username:{} -> UsernameAlreadyExistException", username);
      throw new UsernameAlreadyExistException(username);
    }
  }
}

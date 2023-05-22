package org.up.finance.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.up.finance.entity.User;
import org.up.finance.repository.base.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

  @Query(value = "SELECT u.is_activated FROM user u WHERE u.email = :email", nativeQuery = true)
  boolean isActivated(String email);

  @Transactional
  @Modifying
  @Query(value = "UPDATE user u SET u.is_activated = true WHERE u.email = :email", nativeQuery = true)
  void activeUser(@Param("email") String email);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  Optional<User> findByEmail(String email);
}

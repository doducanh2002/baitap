package org.aibles.authentication.repository;

import org.aibles.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
  Boolean existsByUsernameAndPassword(String username, String password);
}

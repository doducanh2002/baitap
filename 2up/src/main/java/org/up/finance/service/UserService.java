package org.up.finance.service;

import org.up.finance.entity.User;

public interface UserService {
  /**
   * active user, allow user access system
   * @param email - email of user register
   */
  void active(String email);

  /**
   * check if the user is activated in the system
   * @param email - user's email registered
   * @return true if activated or false if not activated
   */
  boolean checkActivated(String email);
  /**
   * create user and save database
   * @param username - username of user register
   * @param password - password encode of user register
   * @param email - email of user register
   * @param fullName - full name of user register
   * @return - information user register
   */
  User create(String username, String password, String email, String fullName);

  /**
   * check exists user in system
   * @param email - email of user
   * @return - true if user exist or false
   */
  boolean existsByEmail(String email);

  /**
   * get user by email of user
   * @param email - email of user registered
   * @return info user
   */
  User findByEmail(String email);

  /**
   * get user by id
   * @param id - id of user
   * @return user info
   */
  User get(String id);
}

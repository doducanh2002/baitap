package org.up.finance.service;

public interface AuthenticationTokenService {

  /**
   * generate access token
   * @param userId - id of user
   * @param email - email of user
   * @param username - username of user
   * @param fullName - full name of user
   * @return access token
   */
  String generateAccessToken(
        String userId,
        String email,
        String username,
        String fullName
  );

  /**
   * generate refresh token
   * @param userId - id of user
   * @param email - email of user
   * @param username - username of user
   * @param fullName - full name of user
   * @return refresh token
   */
  String generateRefreshToken(
        String userId,
        String email,
        String username,
        String fullName
  );

  /**
   * get subject(userId) from access token
   * @param accessToken - access token from login response
   * @return user id
   */
  String getSubjectFromAccessToken(String accessToken);

  /**
   * validate access token
   * @param accessToken - access token from login response
   * @param userId
   * @return true if valid or false if invalid
   */
  boolean validateAccessToken(String accessToken, String userId);

}

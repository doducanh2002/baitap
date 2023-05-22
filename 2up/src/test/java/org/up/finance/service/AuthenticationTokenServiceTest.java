package org.up.finance.service;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.entity.User;

@WebMvcTest(AuthenticationTokenService.class)
@ContextConfiguration(classes = {
      FinanceServiceTestConfiguration.class,
      RedisConfiguration.class
})
public class AuthenticationTokenServiceTest {
  @Autowired
  private AuthenticationTokenService authenticationTokenService;

  private final String USER_ID_TEST = "userID";

  private User mockUserEntity() {
    return User.of(
          "luatnq",
          "Luat@123",
          "luatnq@gmail.com",
          "Nguyen Quoc Luat"
    );
  }

  @Test
  public void testCaseGenerateAccessToken_WhenInputValid_Return_AccessToken() {
    User userEntity = mockUserEntity();
    String accessToken = authenticationTokenService.generateAccessToken(
          USER_ID_TEST,
          userEntity.getEmail(),
          userEntity.getUsername(),
          userEntity.getFullName()
    );

    Assertions.assertNotNull(accessToken);
  }

  @Test
  public void testCaseGenerateRefreshToken_WhenInputValid_ReturnRefreshToken() {
    User userEntity = mockUserEntity();
    String refreshToken = authenticationTokenService.generateRefreshToken(
          USER_ID_TEST,
          userEntity.getEmail(),
          userEntity.getUsername(),
          userEntity.getFullName()
    );

    Assertions.assertNotNull(refreshToken);
  }

  @Test
  public void testCaseGetSubjectFromAccessToken_WhenAccessTokenValid_ReturnSubject() {
    User userEntity = mockUserEntity();
    String accessToken = authenticationTokenService.generateAccessToken(
          USER_ID_TEST,
          userEntity.getEmail(),
          userEntity.getUsername(),
          userEntity.getFullName()
    );
    String userId = authenticationTokenService.getSubjectFromAccessToken(accessToken);
    Assertions.assertEquals(USER_ID_TEST, userId);
  }

  @Test
  public void testCaseValidateAccessToken_WhenAccessTokenValid_ReturnTrue() {
    User userEntity = mockUserEntity();
    String accessToken = authenticationTokenService.generateAccessToken(
          USER_ID_TEST,
          userEntity.getEmail(),
          userEntity.getUsername(),
          userEntity.getFullName()
    );
    boolean isValid = authenticationTokenService.validateAccessToken(accessToken, USER_ID_TEST);
    Assertions.assertEquals(true, isValid);
  }

  @Test
  public void testCaseValidateAccessToken_WhenAccessTokenInValid_ThrowMalformedJwtException() {
    String accessToken = "accessToken";
    Assertions.assertThrowsExactly(
          MalformedJwtException.class,
          () -> authenticationTokenService.validateAccessToken(accessToken, USER_ID_TEST)
    );
  }
}

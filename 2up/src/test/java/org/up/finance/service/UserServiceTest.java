package org.up.finance.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.entity.User;
import org.up.finance.exception.EmailAlreadyExistException;
import org.up.finance.exception.UsernameAlreadyExistException;
import org.up.finance.repository.UserRepository;

@WebMvcTest(UserService.class)
@ContextConfiguration(classes = {
      FinanceServiceTestConfiguration.class,
      RedisConfiguration.class
})
public class UserServiceTest {
  @Autowired
  private UserService service;

  @MockBean
  private UserRepository repository;

  private User mockUserEntity() {
    return User.of(
          "luatnq",
          "Luat@123",
          "luatnq@gmail.com",
          "Nguyen Quoc Luat"
    );
  }

  @Test
  public void testCreateUser_WhenInputValid_ReturnUserInfoSaved() {
    User userEntityReq = mockUserEntity();
    Mockito.when(repository.save(userEntityReq)).thenReturn(userEntityReq);
    User userEntityRes = service.create(
          userEntityReq.getUsername(),
          userEntityReq.getPassword(),
          userEntityReq.getEmail(),
          userEntityReq.getFullName()
    );
    Assertions.assertEquals(userEntityReq.getUsername(), userEntityRes.getUsername());
    Assertions.assertEquals(userEntityReq.getEmail(), userEntityRes.getEmail());
    Assertions.assertEquals(userEntityReq.getFullName(), userEntityRes.getFullName());
    Assertions.assertEquals(false, userEntityRes.isActivated());
  }

  @Test
  public void testCreateUser_WhenUsernameAlready_ReturnBadRequestException() {
    User userEntity = mockUserEntity();

    Mockito.when(repository.existsByUsername(userEntity.getUsername())).thenReturn(true);
    Assertions.assertThrowsExactly(
          UsernameAlreadyExistException.class,
          () -> service.create(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFullName()
          )
    );
  }

  @Test
  public void testCreateUser_WhenEmailAlready_ReturnBadRequestException() {
    User userEntity = mockUserEntity();

    Mockito.when(repository.existsByEmail(userEntity.getEmail())).thenReturn(true);
    Assertions.assertThrowsExactly(
          EmailAlreadyExistException.class,
          () -> service.create(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFullName()
          )
    );
  }

  @Test
  public void testActiveUser_WhenEmailValid_UserActivated() {
    User user = mockUserEntity();
    Mockito.doNothing().when(repository).activeUser(user.getEmail());
    service.active(user.getEmail());
    Mockito.verify(repository, Mockito.times(1)).activeUser(user.getEmail());
  }

}

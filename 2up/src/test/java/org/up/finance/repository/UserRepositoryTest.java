package org.up.finance.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.up.finance.entity.User;

@DataJpaTest
public class UserRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private UserRepository userRepository;

  private User mockUserEntity() {
    return User.of(
          "luatnq",
          "luatnq",
          "luatnq@gmail.com",
          "Nguyen Quoc Luat"
    );
  }

  @Test
  public void testSaveMethod_WhenInputValid_ReturnUserEntity() {
    User userEntity = mockUserEntity();
    userEntity = entityManager.persist(userEntity);
    entityManager.flush();

    User userSaved = userRepository.save(userEntity);

    Assertions.assertEquals(userEntity.getId(), userSaved.getId());
    Assertions.assertEquals(userEntity.getUsername(), userSaved.getUsername());
    Assertions.assertEquals(userEntity.getFullName(), userSaved.getFullName());
    Assertions.assertEquals(userEntity.getEmail(), userSaved.getEmail());
    Assertions.assertEquals(userEntity.getPassword(), userSaved.getPassword());
  }

}

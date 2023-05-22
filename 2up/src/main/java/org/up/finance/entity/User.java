package org.up.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.up.finance.entity.base.BaseEntityWithUpdater;

@Entity
@Table(name = "\"user\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntityWithUpdater {
  private String username;

  private String password;

  private String fullName;

  private String email;

  private boolean isActivated;

  public static User of(String username, String password, String email, String fullName) {
    return User.builder()
          .username(username)
          .password(password)
          .email(email)
          .isActivated(false)
          .fullName(fullName)
          .build();
  }
}

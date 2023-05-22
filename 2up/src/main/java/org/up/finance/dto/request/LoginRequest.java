package org.up.finance.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.up.finance.validation.ValidationEmail;
import org.up.finance.validation.ValidationPassword;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class LoginRequest {
  @ValidationEmail
  private String email;

  @ValidationPassword
  private String password;
}

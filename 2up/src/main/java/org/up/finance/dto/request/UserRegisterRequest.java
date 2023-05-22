package org.up.finance.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.up.finance.validation.ValidationConfirmPassword;
import org.up.finance.validation.ValidationEmail;
import org.up.finance.validation.ValidationPassword;
import org.up.finance.validation.ValidationUsername;

@Data
@Slf4j
@AllArgsConstructor(staticName = "from")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ValidationConfirmPassword(originalField = "password", confirmationField = "confirmPassword")
public class UserRegisterRequest {
  @ValidationEmail
  @NotBlank
  private String email;
  @ValidationUsername
  @NotBlank
  private String username;
  private String fullName;
  @NotBlank
  @Size(min = 8)
  @ValidationPassword
  private String password;
  @NotBlank
  @Size(min = 8)
  @ValidationPassword
  private String confirmPassword;

}

package org.up.finance.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.up.finance.validation.ValidationEmail;
import org.up.finance.validation.ValidationOTP;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ActiveUserRequest {
  @ValidationEmail
  @NotBlank
  private String email;
  @ValidationOTP
  @NotBlank
  private String otp;
}

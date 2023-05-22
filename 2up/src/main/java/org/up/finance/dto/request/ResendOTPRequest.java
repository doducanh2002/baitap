package org.up.finance.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.up.finance.validation.ValidationEmail;

@Data
public class ResendOTPRequest {
  @ValidationEmail
  @NotBlank
  private String email;
}

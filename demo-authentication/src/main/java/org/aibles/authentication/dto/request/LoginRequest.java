package org.aibles.authentication.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

  @NotBlank(message = "username can't blank!")
  private String username;

  @NotBlank(message = "password can't blank!")
  private String password;

}

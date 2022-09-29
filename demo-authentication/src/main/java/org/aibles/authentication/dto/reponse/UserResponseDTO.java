package org.aibles.authentication.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

  @NotBlank(message = "username can't blank!")
  private String username;

  @NotBlank(message = "password can't blank!")
  private String password;

  @NotBlank(message = "email can't blank!")
  private String email;
}

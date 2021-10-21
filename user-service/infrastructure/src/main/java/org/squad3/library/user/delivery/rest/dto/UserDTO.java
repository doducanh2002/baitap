package org.squad3.library.user.delivery.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String name;
   private String email;
   private String phone;
  private AccountDTO account;
  private String role;

}

package org.squad3.library.user.delivery.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDTO  {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private AccountDTO account;
    private String role;
}

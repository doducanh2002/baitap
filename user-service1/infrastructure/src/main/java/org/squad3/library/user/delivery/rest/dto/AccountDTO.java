package org.squad3.library.user.delivery.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class AccountDTO {

    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

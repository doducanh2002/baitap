package org.squad3.library.user.delivery.rest.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountDTO {

    private String username;

    private String password;
}

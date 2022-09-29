package org.aibles.authentication.dto.request;

import lombok.Data;
import org.aibles.authentication.entity.User;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {

    @NotBlank(message = "username can't blank!")
    private String username;

    @NotBlank(message = "password can't blank!")
    private String password;

    @NotBlank(message = "email can't blank!")
    private String email;

    public User toUser() {
        User user = new User();
        user.setUsername(this.getUsername());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        return user;
    }


}

package org.squad3.library.user;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.squad3.library.shared.SelfValidating;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class User extends SelfValidating<User> implements Serializable {

    private Integer id;

    @NotBlank(message = "The user's name must not be null.")
    @Length(max = 30, message = "Max length of name is 30 characters.")
    private String name;

    @NotBlank(message = "The email must not be null.")
    @Email(message = "The email invalid form.")
    @Length(max = 50, message = "Max length of email is 50 characters.")
    private String email;

    @Length(min = 10, max = 10, message = "The number phone must have 10 digits")
    private String phone;

    @NotNull(message = "The role must not be null.")
    private @Valid Role role;

    @NotNull(message = "Account must not be null.")
    private @Valid Account account;

    @Override
    public boolean equals(Object o) {
        if (this ==o)   return true;
        if (!(o instanceof User))   return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

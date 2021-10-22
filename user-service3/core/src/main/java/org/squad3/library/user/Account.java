package org.squad3.library.user;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.squad3.library.shared.SelfValidating;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class Account extends SelfValidating<Account> implements Serializable {

    private Integer id;

    @NotBlank(message = "The username of account must not be null.")
    @Length(max = 20, message = "Max length of username is 20 characters.")
    private String username;

    @NotBlank(message = "The password of account must not be null.")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this ==o)   return true;
        if (!(o instanceof Account))   return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

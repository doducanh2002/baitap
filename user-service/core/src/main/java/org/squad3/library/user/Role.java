package org.squad3.library.user;

import lombok.Builder;
import lombok.Data;
import org.squad3.library.shared.SelfValidating;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class Role extends SelfValidating<Role> implements Serializable {

    private int id;

    @NotBlank(message = "The roleName must not be null.")
    private String roleName;

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

package org.squad3.library.document;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.squad3.library.shared.SelfValidating;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class Category extends SelfValidating<Category> implements Serializable {

    private Integer id;

    @NotBlank(message = "The name of account must not be null.")
    @Length(max = 20, message = "Max length of name is 20 characters.")
    private String name;

    @NotBlank(message = "The created by must not be null.")
    @Length(max = 30, message = "Max length of created by is 30 characters.")
    private String createdBy;

    @NotBlank(message = "The updated by must not be null.")
    @Length(max = 30, message = "Max length of updated by is 30 characters.")
    private String updatedBy;


    @Override
    public boolean equals(Object o) {
        if (this ==o)   return true;
        if (!(o instanceof Category))   return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

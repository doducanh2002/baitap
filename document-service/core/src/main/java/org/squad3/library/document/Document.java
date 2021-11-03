package org.squad3.library.document;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.squad3.library.shared.SelfValidating;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Document extends SelfValidating<Document> implements Serializable {

    private Integer id;

    @NotBlank(message = "The document's name must not be null.")
    @Length(max = 20, message = "Max length of name is 20 characters.")
    private String name;

    @NotBlank(message = "The author must not be null.")
    @Length(max = 30, message = "Max length of author is 30 characters.")
    private String author;

    @NotBlank(message = "The description  must not be null.")
    @Length(max = 160, message = "Max length of description is 160 characters.")
    private String description ;

    @NotBlank(message = "The title must not be null.")
    @Length(max = 65, message = "Max length of title is 65 characters.")
    private String title;

    @Length(max = 65, message = "Max length of file is 65 characters.")
    private String file;

    @Length(max = 90, message = "Max length of thumb is 90 characters.")
    private String thumb;

    @NotBlank(message = "The type by must not be null.")
    @Length(max = 10, message = "Max length of type is 30 characters.")
    private String type;

    @NotBlank(message = "The created by must not be null.")
    @Length(max = 30, message = "Max length of created by is 30 characters.")
    private String createdBy;

    @NotBlank(message = "The updated by must not be null.")
    @Length(max = 30, message = "Max length of updated by is 30 characters.")
    private String updatedBy;

    @NotNull(message = "Category must not be null.")
    private @Valid Category category;

    @Override
    public boolean equals(Object o) {
        if (this ==o)   return true;
        if (!(o instanceof Document))   return false;
        Document document = (Document) o;
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

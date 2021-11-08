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

    @NotBlank(message = "The title must not be null.")
    @Length(max = 128, message = "Max length of title is 128 characters.")
    private String title;

    @NotBlank(message = "The description  must not be null.")
    private String description ;

    @NotBlank(message = "The author must not be null.")
    @Length(max = 32, message = "Max length of author is 32 characters.")
    private String author;

    @Length(max =256, message = "Max length of file_s3_object_key is 256 characters.")
    private String file_s3_object_key;

    @Length(max = 256, message = "Max length of objectKey_thumb is 256 characters.")
    private String thumb_s3_object_key;

    @NotBlank(message = "The type by must not be null.")
    @Length(max = 16, message = "Max length of type is 16 characters.")
    private String type;

    @NotBlank(message = "The created by must not be null.")
    private Integer createdBy;

    @NotBlank(message = "The updated by must not be null.")
    private Integer last_UpdatedBy;

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

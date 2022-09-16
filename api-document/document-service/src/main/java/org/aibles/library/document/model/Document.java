package org.aibles.library.document.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aibles.library.document.validation.BaseValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document")
public class Document extends BaseValidation<Document> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Title can not be null")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Description can not be null")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Author can not be null")
    @Column(name = "author")
    private String author;

    @NotBlank(message = "File S3 Object Key can not be null")
    @Column(name = "file_s3_object_key")
    private String fileS3ObjectKey;

    @Column(name = "thumb_s3_object_key")
    private String thumbS3ObjectKey;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DocsType type;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "category_document",
            joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        Document obj = (Document) o;
        return Objects.equals(getId(), obj.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}

package org.squad3.library.documentservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document")
public class Document extends BaseEntity implements Serializable {

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
    private Integer createdBy;

    @Column(name = "last_updated_by")
    private Integer lastUpdatedBy;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "document")
    private List<CategoryDocument> categoryDocuments ;

//    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<CategoryDocument> getCategoryDocuments(){
//        return categoryDocuments;
//    }
//
//    private void setCategoryDocuments(Set<CategoryDocument> categoryDocuments){
//        this.categoryDocuments = categoryDocuments;
//    }

//    public List<Category> getCategory(){
//        return this.categoryDocuments.stream()
//                .map(CategoryDocument::getCategory).collect(Collectors.toList());
//    }

//    @ManyToMany
//    @JoinTable(name = "category_document",joinColumns = @JoinColumn(name = "document_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
//    private List<Category> categories ;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "category_document",
//            joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColum n(name = "category_id", referencedColumnName = "id"))
//    private Set<Category> categories ;

    public void setDocumentCategories(List<CategoryDocument> documentCategories) {
        Optional.ofNullable(documentCategories)
                .ifPresent(dcs -> {
                    this.categoryDocuments = dcs;
                    this.getCategoryDocuments().stream()
                            .map(documentCategory -> {
                                documentCategory.setDocument(this);
                                return documentCategory;
                            })
                            .collect(Collectors.toSet());
                });
    }

    public Set<Category> getCategories(){
        return this.categoryDocuments.stream()
                .map(CategoryDocument::getCategory).collect(Collectors.toSet());
    }

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

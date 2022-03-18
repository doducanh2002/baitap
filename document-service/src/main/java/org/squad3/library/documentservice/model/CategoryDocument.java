package org.squad3.library.documentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category_document")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDocument extends BaseEntity implements Serializable {

    @EmbeddedId
    CategoryDocumentKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("documentId")
    @JoinColumn(name = "document_id")
    private Document document;

    public CategoryDocument(CategoryDocumentKey id, Category category) {
        this.id = id;
        this.category = category;
    }
}

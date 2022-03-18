package org.squad3.library.documentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDocumentKey implements Serializable {

    @Column(name = "document_id")
    private int documentId;

    @Column(name = "category_id")
    private int categoryId;

    public CategoryDocumentKey(int categoryId) {
        this.categoryId = categoryId;
    }
}

package org.squad3.library.document.persistance.converters;

import org.squad3.library.document.Category;
import org.squad3.library.document.Document;
import org.squad3.library.document.delivery.rest.dto.DocumentDTO;
import org.squad3.library.document.persistance.entites.CategoryEntity;
import org.squad3.library.document.persistance.entites.DocumentEntity;
import org.squad3.library.shared.RepositoryConverter;

import java.util.Optional;

public class DocumentRepositoryConverter implements RepositoryConverter<DocumentEntity, Document> {

    @Override
    public DocumentEntity mapToTable(Document documentPersistence) {
        return Optional.ofNullable(documentPersistence)
                .map(ap -> {
                    return DocumentEntity.builder()
                            .name(ap.getName())
                            .author(ap.getAuthor())
                            .description(ap.getDescription())
                            .createdBy(ap.getCreatedBy())
                            .updateBy(ap.getUpdatedBy())
                            .thumb(ap.getThumb())
                            .title(ap.getTitle())
                            .type(ap.getType())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public Document mapToEntity(DocumentEntity documentTable) {
        return Optional.ofNullable(documentTable)
                .map(at -> {
                    return Document.builder()
                            .id(at.getId())
                            .name(at.getName())
                            .author(at.getAuthor())
                            .description(at.getDescription())
                            .createdBy(at.getCreatedBy())
                            .updatedBy(at.getUpdateBy())
                            .thumb(at.getThumb())
                            .title(at.getTitle())
                            .type(at.getType())
                            .build();
                })
                .orElse(null);
    }
}

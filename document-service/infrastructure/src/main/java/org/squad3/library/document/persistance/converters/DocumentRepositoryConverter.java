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
                            .author(ap.getAuthor())
                            .description(ap.getDescription())
                            .createdBy(ap.getCreatedBy())
                            .lastUpdateBy(ap.getLast_UpdatedBy())
                            .thumb_s3_object_key(ap.getThumb_s3_object_key())
                            .file_s3_object_key(ap.getFile_s3_object_key())
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
                            .author(at.getAuthor())
                            .description(at.getDescription())
                            .createdBy(at.getCreatedBy())
                            .last_UpdatedBy(at.getLastUpdateBy())
                            .file_s3_object_key(at.getFile_s3_object_key())
                            .thumb_s3_object_key(at.getThumb_s3_object_key())
                            .title(at.getTitle())
                            .type(at.getType())
                            .build();
                })
                .orElse(null);
    }
}

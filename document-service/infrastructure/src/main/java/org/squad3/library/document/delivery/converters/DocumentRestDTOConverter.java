package org.squad3.library.document.delivery.converters;

import org.squad3.library.document.Document;
import org.squad3.library.document.delivery.rest.dto.DocumentDTO;
import org.squad3.library.shared.RestDTOConverter;

import java.util.Optional;

public class DocumentRestDTOConverter implements RestDTOConverter<DocumentDTO, Document> {

    @Override
    public Document mapToEntity(DocumentDTO documentDTO) {
        return Optional.ofNullable(documentDTO)
                .map(adto -> {
                    return Document.builder()
                            .author(adto.getAuthor())
                            .description(adto.getDescription())
                            .title(adto.getTitle())
                            .file_s3_object_key(adto.getFile_s3_object_key())
                            .thumb_s3_object_key(adto.getThumb_s3_object_key())
                            .type(adto.getType())
                            .createdBy(adto.getCreateBy())
                            .last_UpdatedBy(adto.getLastUpdateBy())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public DocumentDTO mapToDTO(Document document) {
        return Optional.ofNullable(document)
                .map(a -> {
                    return DocumentDTO.builder()
                            .author(a.getAuthor())
                            .description(a.getDescription())
                            .title(a.getTitle())
                            .file_s3_object_key(a.getFile_s3_object_key())
                            .thumb_s3_object_key(a.getThumb_s3_object_key())
                            .type(a.getType())
                            .createBy(a.getCreatedBy())
                            .lastUpdateBy(a.getLast_UpdatedBy())
                            .build();
                })
                .orElse(null);
    }
}

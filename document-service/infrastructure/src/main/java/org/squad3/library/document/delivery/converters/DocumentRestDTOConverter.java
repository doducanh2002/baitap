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
                            .name(adto.getName())
                            .author(adto.getAuthor())
                            .description(adto.getDescription())
                            .title(adto.getTitle())
                            .file(adto.getFile())
                            .thumb(adto.getThumb())
                            .type(adto.getType())
                            .createdBy(adto.getCreateBy())
                            .updatedBy(adto.getUpdateBy())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public DocumentDTO mapToDTO(Document document) {
        return Optional.ofNullable(document)
                .map(a -> {
                    return DocumentDTO.builder()
                            .name(a.getName())
                            .author(a.getAuthor())
                            .description(a.getDescription())
                            .title(a.getTitle())
                            .file(a.getFile())
                            .thumb(a.getThumb())
                            .type(a.getType())
                            .createBy(a.getCreatedBy())
                            .updateBy(a.getUpdatedBy())
                            .build();
                })
                .orElse(null);
    }
}

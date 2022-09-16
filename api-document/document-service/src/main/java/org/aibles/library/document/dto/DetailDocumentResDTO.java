package org.aibles.library.document.dto;

import lombok.Data;
import org.aibles.library.document.model.DocsType;

@Data
public class DetailDocumentResDTO extends BasicDocumentResDTO {

    private String fileS3ObjectKey;

    private String createdBy;

    private String lastUpdatedBy;

    private DocsType type;

}

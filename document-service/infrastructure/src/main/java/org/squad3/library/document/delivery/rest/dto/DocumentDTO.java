package org.squad3.library.document.delivery.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDTO {

    private String author;

    private String description;

    private String file_s3_object_key;

    private String thumb_s3_object_key;

    private String title;

    private String type;

    private Integer createBy;

    private Integer lastUpdateBy;

}

package org.squad3.library.document.delivery.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentDTO {

    private String name;

    private String author;

    private String description;

    private String file;

    private String thumb;

    private String title;

    private String type;

    private String createBy;

    private String updateBy;

}

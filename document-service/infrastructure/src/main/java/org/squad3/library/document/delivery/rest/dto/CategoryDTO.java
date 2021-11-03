package org.squad3.library.document.delivery.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

    private String name;

    private String createdBy;

    private String updatedBy;
}

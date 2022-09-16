package org.aibles.library.document.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class BasicDocumentResDTO {

    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private  String author;

    @NotBlank
    private String thumbS3ObjectKey;

    private Set<CategoryDTO> categories;
}

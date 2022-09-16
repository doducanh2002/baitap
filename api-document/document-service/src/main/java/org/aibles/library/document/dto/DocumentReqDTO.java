package org.aibles.library.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReqDTO {

    @NotBlank(message = "Title can not be null")
    private String title;

    @NotBlank(message = "Description can not be null")
    private String description;

    @NotBlank(message = "Author can not be null")
    private  String author;

    @NotBlank(message = "File S3 Object Key can not be null")
    private String fileS3ObjectKey;

    private String thumbS3ObjectKey;

//    @NotBlank(message =  "Type not null")
//    private DocsType type;

    private Set<Integer> categoryIds;

}

package org.squad3.library.documentservice.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DocumentRes {

    private int id;

    private String title;

    private String description;

    private  String author;

    private String thumbS3ObjectKey;

    private List<CategoryDTO> categories;
}

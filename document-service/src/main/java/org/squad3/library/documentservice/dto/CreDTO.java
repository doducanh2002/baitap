package org.squad3.library.documentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.squad3.library.documentservice.model.DocsType;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreDTO implements Serializable {

    private int id;

    private String title;

    private String description;

    private  String author;

    private String fileS3ObjectKey;

    private String thumbS3ObjectKey;

    private int createdBy;

    private int lastUpdatedBy;

    private DocsType type;

    private List<Integer> categoryIds;

}

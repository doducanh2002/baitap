package org.squad3.library.documentservice.dto;

import com.amazonaws.services.timestreamquery.model.transform.CancelQueryRequestProtocolMarshaller;
import lombok.Data;
import org.squad3.library.documentservice.model.Category;
//import org.squad3.library.documentservice.model.CategoryDocument;
//import org.squad3.library.documentservice.model.CategoryDocument;
//import org.squad3.library.documentservice.model.CategoryDocumentKey;
import org.squad3.library.documentservice.model.DocsType;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class DocumentDTO implements Serializable {

    private int id;

    private String title;

    private String description;

    private  String author;

    private String fileS3ObjectKey;

    private String thumbS3ObjectKey;

    private int createdBy;

    private int lastUpdatedBy;

    private DocsType type;

    private List<CategoryDTO> categories;

}

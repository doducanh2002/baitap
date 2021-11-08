package org.squad3.library.document.persistance.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document")
@Builder
public class DocumentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "author", length = 32)
    private String author;

    private String description;

    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "thumb_s3_object_key", length = 256)
    private String thumb_s3_object_key;

    @Column(name = "file_s3_object_key", length = 256)
    private String file_s3_object_key;

    @Column(name = "type", length = 16)
    private String type;

    @Column(name = "createdBy")
    private Integer createdBy;

    @Column(name = "updateBy")
    private Integer lastUpdateBy;


    @Override
    public int hashCode() {
        return 31;
    }


}



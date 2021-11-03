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

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "author", length = 20)
    private String author;

    @Column(name = "description", length = 160)
    private String description;

    @Column(name = "title", length = 65)
    private String title;

    @Column(name = "thumb", length = 90)
    private String thumb;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "createdBy", length = 30)
    private String createdBy;

    @Column(name = "updateBy", length = 30)
    private String updateBy;


    @Override
    public int hashCode() {
        return 31;
    }


}



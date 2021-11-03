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
@Table(name = "category")
@Builder
public class CategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "createdBy", length = 30)
    private String createdBy;

    @Column(name = "updateBy", length = 30)
    private String updateBy;

    @Override
    public int hashCode() {
        return 31;
    }


}


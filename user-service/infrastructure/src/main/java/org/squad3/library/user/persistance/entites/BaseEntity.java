package org.squad3.library.user.persistance.entites;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_date")
    protected Instant createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    protected Instant updatedDate;
}

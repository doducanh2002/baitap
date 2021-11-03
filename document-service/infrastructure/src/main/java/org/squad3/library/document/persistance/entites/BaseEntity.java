package org.squad3.library.document.persistance.entites;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    protected Instant createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    protected Instant updatedDate;
}

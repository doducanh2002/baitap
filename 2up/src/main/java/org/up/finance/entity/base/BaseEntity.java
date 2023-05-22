package org.up.finance.entity.base;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
  @Id
  protected String id;

  @CreatedBy
  protected String createdBy;

  @CreatedDate
  protected Long createdAt;

  @PrePersist
  public void ensureId() {
    this.id = StringUtils.isBlank(this.id) ? UUID.randomUUID().toString() : this.id;
  }
}

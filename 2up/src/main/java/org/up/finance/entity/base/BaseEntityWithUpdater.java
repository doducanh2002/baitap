package org.up.finance.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Data
public class BaseEntityWithUpdater extends BaseEntity {

  @LastModifiedDate
  protected Long lastUpdatedAt;

  @LastModifiedBy
  protected String lastUpdatedBy;
}

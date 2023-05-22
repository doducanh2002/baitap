package org.up.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.up.finance.constant.TransactionType;
import org.up.finance.dto.request.TransactionRequest;
import org.up.finance.entity.base.BaseEntityWithUpdater;

@Data
@Entity
@Table(name = "finance_transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntityWithUpdater {

  @Enumerated(EnumType.STRING)
  private TransactionType type;
  private Long amount;
  private String note;
  private String description;
  private String walletId;
  private String categoryId;
  private String userId;

  public static Transaction from(String userId, TransactionRequest request) {
    return Transaction.builder()
        .type(TransactionType.valueOf(request.getType()))
        .amount(request.getAmount())
        .note(request.getNote())
        .description(request.getDescription())
        .walletId(request.getWalletId())
        .categoryId(request.getCategoryId())
        .userId(userId)
        .build();
  }
}

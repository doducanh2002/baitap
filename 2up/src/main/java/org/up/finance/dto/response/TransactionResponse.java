package org.up.finance.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.up.finance.constant.TransactionType;
import org.up.finance.entity.Transaction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class TransactionResponse {

  private String id;
  private TransactionType type;
  private long amount;
  private String note;
  private String description;
  private String walletId;
  private String categoryId;
  private long createdAt;
  private long updatedAt;

  public static TransactionResponse from(Transaction transaction) {
    return TransactionResponse.builder()
        .id(transaction.getId())
        .type(transaction.getType())
        .amount(transaction.getAmount())
        .note(transaction.getNote())
        .description(transaction.getDescription())
        .walletId(transaction.getWalletId())
        .categoryId(transaction.getCategoryId())
        .createdAt(transaction.getCreatedAt())
        .updatedAt(transaction.getLastUpdatedAt())
        .build();
  }
}

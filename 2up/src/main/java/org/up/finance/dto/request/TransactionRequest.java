package org.up.finance.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.up.finance.constant.TransactionType;
import org.up.finance.validation.ValidateEnum;

@Data
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class TransactionRequest {

  @NotBlank
  @ValidateEnum(enumClazz = TransactionType.class)
  private String type;
  @NotNull
  private Long amount;
  private String note;
  private String description;
  @NotBlank
  private String walletId;
  @NotBlank
  private String categoryId;
}

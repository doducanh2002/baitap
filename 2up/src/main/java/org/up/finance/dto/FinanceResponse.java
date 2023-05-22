package org.up.finance.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.up.finance.util.DateUtils;

import static org.up.finance.constant.FinanceConstant.MessageResponse.SUCCESS;

@AllArgsConstructor(staticName = "of")
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FinanceResponse<T> {
  private int status;
  private String statusMessage;
  private T data;
  private String timestamp;

  public static <T> FinanceResponse<T> of(int status, String statusMessage, T data) {
    return FinanceResponse.of(status, statusMessage, data, DateUtils.getCurrentDateString());
  }

  public static <T> FinanceResponse<T> of(int status, String statusMessage) {
    return FinanceResponse.of(status, statusMessage, null, DateUtils.getCurrentDateString());
  }

  public static <T> FinanceResponse<T> successOf(int status, T data) {
    return FinanceResponse.of(status, SUCCESS, data);
  }

  public static <T> FinanceResponse<T> successOf(int status) {
    return FinanceResponse.of(status, SUCCESS, null, DateUtils.getCurrentDateString());
  }
}

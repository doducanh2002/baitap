package org.up.finance.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginResponse {
  protected String accessToken;
  protected String refreshToken;
  protected Long accessTokenLifeTime;
  protected Long refreshTokenLifeTime;
  protected final String tokenType = "Bearer";

  public static LoginResponse of(
        String accessToken,
        String refreshToken,
        Long accessTokenLifeTime,
        Long refreshTokenLifeTime
  ) {
    return LoginResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .accessTokenLifeTime(accessTokenLifeTime)
          .refreshTokenLifeTime(refreshTokenLifeTime)
          .build();
  }
}

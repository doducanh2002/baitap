package org.up.finance.security.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ptit.okrs.core_util.DateUtils;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

import static org.up.finance.constant.FinanceConstant.AuthConstant.UNAUTHENTICATED;
import static org.up.finance.constant.FinanceConstant.AuthConstant.UNAUTHENTICATED_STATUS;
import static org.up.finance.constant.FinanceConstant.ContentType.JSON_APPLICATION;

@Component
public class UnAuthenticationCustomHandler implements AuthenticationEntryPoint {
  @Override
  public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
  ) throws IOException {
    var error = new HashMap<String, Object>();
    error.put("status", HttpStatus.UNAUTHORIZED.value());
    error.put("status_message", HttpStatus.UNAUTHORIZED.toString());
    error.put("timestamp", DateUtils.getCurrentDateTimeStr());
    var data = new HashMap<String, Object>();
    data.put("message", UNAUTHENTICATED);
    error.put("data", data);
    response.setContentType(JSON_APPLICATION);
    response.setStatus(UNAUTHENTICATED_STATUS);
    response.getWriter().write(new ObjectMapper().writeValueAsString(error));
  }
}

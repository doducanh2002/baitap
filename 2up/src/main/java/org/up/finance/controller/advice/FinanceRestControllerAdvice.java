package org.up.finance.controller.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.up.finance.dto.Error;
import org.up.finance.dto.FinanceResponse;
import org.up.finance.exception.base.BaseException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class FinanceRestControllerAdvice {

  private final MessageSource messageSource;

  @ExceptionHandler(value = {BaseException.class})
  public ResponseEntity<FinanceResponse<Error>> handleFinanceBaseException(
        BaseException ex, WebRequest webRequest
  ) {
    return ResponseEntity
          .status(ex.getStatus())
          .body(getError(ex.getStatus(), ex.getCode(), webRequest.getLocale(), ex.getParams()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<FinanceResponse<Error>> handleValidationExceptions(
        MethodArgumentNotValidException exception) {
    log.error("(handleValidationExceptions)exception: {}", exception.getMessage());

    String errorMessage = exception.getBindingResult().getFieldErrors().stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .findFirst()
          .orElse(exception.getMessage());

    return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(getError(HttpStatus.BAD_REQUEST.value(), exception.getTitleMessageCode(), errorMessage));
  }

  private FinanceResponse<Error> getError(int status, String code, Locale locale, Map<String, String> params) {
    return FinanceResponse.of(
          status,
          HttpStatus.valueOf(status).getReasonPhrase(),
          Error.of(code, getMessage(code, locale, params))
    );
  }

  private FinanceResponse<Error> getError(int status, String code, String message) {
    return FinanceResponse.of(
          status,
          HttpStatus.valueOf(status).getReasonPhrase(),
          Error.of(code, message)
    );
  }

  private String getMessage(String code, Locale locale, Map<String, String> params) {
    var message = getMessage(code, locale);
    if (params != null && !params.isEmpty()) {
      for (var param : params.entrySet()) {
        message = message.replace(getMessageParamsKey(param.getKey()), param.getValue());
      }
    }
    return message;
  }

  private String getMessage(String code, Locale locale) {
    try {
      return messageSource.getMessage(code, null, locale);
    } catch (Exception ex) {
      return code;
    }
  }

  private String getMessageParamsKey(String key) {
    return "%" + key + "%";
  }
}

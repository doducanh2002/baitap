package org.aibles.authentication.exception;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
public class BaseException extends RuntimeException{
  private String code;
  private int status;
  private Map<String, Object> params;

  public void addParams(String key, Object value) {
    if(this.params == null) {
      params = new HashMap<>();
    }
    params.put(key, value);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
}

package org.up.finance.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Component
public class CorsCustomFilter extends CorsFilter {

  public CorsCustomFilter(CorsConfigurationSource configSource) {
    super(configSource);
  }

}

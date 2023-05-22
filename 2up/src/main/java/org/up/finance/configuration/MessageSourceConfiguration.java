package org.up.finance.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

import static org.up.finance.constant.FinanceConstant.MessageResponse.MESSAGE_FOLDER;
import static org.up.finance.constant.FinanceConstant.UTF_8_ENCODING;

@Configuration
public class MessageSourceConfiguration {

  @Bean
  public LocaleResolver localeResolver() {
    var localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(Locale.forLanguageTag("en"));
    return localeResolver;
  }

  @Bean
  public MessageSource messageSource() {
    var messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename(MESSAGE_FOLDER);
    messageSource.setDefaultEncoding(UTF_8_ENCODING);
    return messageSource;
  }
}

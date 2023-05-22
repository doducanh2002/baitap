package org.up.finance.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

import static org.up.finance.constant.FinanceConstant.EmailConstant.EMAIL_TEMPLATE_PREFIX;
import static org.up.finance.constant.FinanceConstant.EmailConstant.EMAIL_TEMPLATE_SUFFIX;

@Configuration
public class ThymeleafTemplateConfiguration {
  @Bean
  public SpringTemplateEngine springTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(emailTemplateSolver());
    return templateEngine;
  }

  @Bean
  public ClassLoaderTemplateResolver emailTemplateSolver() {
    ClassLoaderTemplateResolver emailTemplateSolver = new ClassLoaderTemplateResolver();
    emailTemplateSolver.setPrefix(EMAIL_TEMPLATE_PREFIX);
    emailTemplateSolver.setSuffix(EMAIL_TEMPLATE_SUFFIX);
    emailTemplateSolver.setTemplateMode(TemplateMode.HTML);
    emailTemplateSolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    emailTemplateSolver.setCacheable(false);
    return emailTemplateSolver;
  }

}

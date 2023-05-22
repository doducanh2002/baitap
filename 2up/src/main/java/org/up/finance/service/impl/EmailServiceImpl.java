package org.up.finance.service.impl;

import jakarta.mail.Message;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.up.finance.exception.InternalServerError;
import org.up.finance.service.EmailService;

import java.util.Map;

import static org.up.finance.constant.FinanceConstant.EmailConstant.CONTENT_TYPE_TEXT_HTML;

@Slf4j
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender emailSender;
  private final SpringTemplateEngine templateEngine;

  public EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
  }

  @Async
  @Override
  public void send(String subject, String to, String template, Map<String, Object> properties) {
    log.info("(send)subject: {}, to: {}, template: {}, properties: {}", subject, to, template, properties);
    try {
      MimeMessage message = emailSender.createMimeMessage();
      message.setRecipients(Message.RecipientType.TO, to);
      message.setSubject(subject);
      message.setContent(getContent(template, properties), CONTENT_TYPE_TEXT_HTML);
      emailSender.send(message);
    } catch (Exception ex) {
      log.info("(send)subject: {}, to: {}, ex: {} ", subject, to, ex.getMessage());
      throw new InternalServerError("Send mail failed to email: {}" + to);
    }
  }

  private String getContent(String template, Map<String, Object> properties) {
    var context = new Context();
    context.setVariables(properties);
    return templateEngine.process(template, context);
  }
}

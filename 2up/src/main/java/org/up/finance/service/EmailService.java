package org.up.finance.service;

import java.util.Map;

public interface EmailService {
  /**
   * send email for any address email
   * @param subject - email's subject sent
   * @param to - address email receive
   * @param template - email's template sent
   * @param properties - properties of email's template
   */
  void send(String subject, String to, String template, Map<String, Object> properties);
}

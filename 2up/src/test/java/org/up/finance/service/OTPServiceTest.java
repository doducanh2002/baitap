package org.up.finance.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.util.GeneratorUtils;

@WebMvcTest(OTPService.class)
@ContextConfiguration(classes = {
      FinanceServiceTestConfiguration.class,
      RedisConfiguration.class}
)
public class OTPServiceTest {

  @Autowired
  private OTPService otpService;

  @Test
  public void testSaveOTP_WhenInputValid_OTPSaved() {
    String otp = GeneratorUtils.generateOTP();
    String key = "keyOTP";
    otpService.set(key, otp);
    Assertions.assertEquals(otp, otpService.get(key));
  }
}

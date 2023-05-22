package org.up.finance.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.up.finance.facade.AuthenticationFacadeService;
import org.up.finance.facade.impl.AuthenticationFacadeServiceImpl;
import org.up.finance.repository.*;
import org.up.finance.service.*;
import org.up.finance.service.impl.*;

import java.util.concurrent.TimeUnit;


@Configuration
public class FinanceServiceConfiguration {

    @Value("${spring.data.redis.timeout:3}")
    private Integer redisOtpTimeOut;

    @Value("${application.token.access.life-time}")
    private Long accessTokenLifeTime;

    @Value("${application.token.access.secret}")
    private String accessTokenSecret;

    @Value("${application.token.refresh.life-time}")
    private Long refreshTokenLifeTime;

    @Value("${application.token.refresh.secret}")
    private String refreshTokenSecret;

    @Bean
    public AuthenticationFacadeService authenticationFacadeService(
          UserService userService,
          OTPService otpService,
          EmailService emailService,
          AuthenticationTokenService authenticationTokenService,
          TokenRedisService tokenRedisService
    ) {
        return new AuthenticationFacadeServiceImpl(
              userService,
              otpService,
              emailService,
              accessTokenLifeTime,
              refreshTokenLifeTime,
              authenticationTokenService,
              tokenRedisService);
    }

    @Bean
    public AuthenticationTokenService authenticationTokenService() {
        return new AuthenticationTokenServiceImpl(
              accessTokenLifeTime,
              accessTokenSecret,
              refreshTokenLifeTime,
              refreshTokenSecret
        );
    }

    @Bean
    public CategoryService categoryService(CategoryRepository repository) {
        return new CategoryServiceImpl(repository);
    }

    @Bean
    public DailyNoteService dailyNoteService(DailyNoteRepository repository) {
        return new DailyNoteServiceImpl(repository);
    }

    @Bean
    public EmailService emailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        return new EmailServiceImpl(emailSender, templateEngine);
    }

    @Bean
    public OTPService otpService(RedisTemplate<String, Object> redisTemplate) {
        return new OTPServiceImpl(redisTemplate, redisOtpTimeOut, TimeUnit.MINUTES);
    }

    @Bean
    public UserService userService(UserRepository repository) {
        return new UserServiceImpl(repository);
    }

    @Bean
    public TokenRedisService tokenRedisService(
          RedisTemplate<String, String> redisTemplateStr
    ) {
        return new TokenRedisServiceImpl(redisTemplateStr);
    }

    @Bean
    public WalletGroupService walletGroupService(WalletGroupRepository repository) {
        return new WalletGroupServiceImpl(repository);
    }

    @Bean
    public WalletService walletService(WalletRepository repository) {
        return new WalletServiceImpl(repository);
    }

//    @Bean
//    public TransactionService transactionService(TransactionRepository repository) {
//        return new TransactionServiceImpl(repository);
//    }
}

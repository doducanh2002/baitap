package org.up.finance.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.up.finance.dto.response.DailyNoteResponse;
import org.up.finance.facade.AuthenticationFacadeService;
import org.up.finance.facade.impl.AuthenticationFacadeServiceImpl;
import org.up.finance.repository.CategoryRepository;
import org.up.finance.repository.UserRepository;
import org.up.finance.repository.WalletGroupRepository;
import org.up.finance.repository.WalletRepository;
import org.up.finance.service.*;
import org.up.finance.security.SecurityUtil;
import org.up.finance.security.impl.SecurityUtilTestImpl;
import org.up.finance.repository.*;
import org.up.finance.service.*;
import org.up.finance.service.impl.*;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@TestConfiguration
@EnableJpaRepositories(basePackages = {"org.up.finance.repository"},
      entityManagerFactoryRef = "testEntityManagerFactory",
      transactionManagerRef = "testTransactionManager")
@ComponentScan(basePackages = {"org.up.finance.repository"})
@EnableTransactionManagement
public class FinanceServiceTestConfiguration {

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
  public AuthenticationTokenService authenticationTokenService() {
    return new AuthenticationTokenServiceImpl(
          accessTokenLifeTime,
          accessTokenSecret,
          refreshTokenLifeTime,
          refreshTokenSecret
    );
  }

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
          tokenRedisService
    );
  }

  @Bean
  public CategoryServiceImpl categoryService(CategoryRepository repository) {
    return new CategoryServiceImpl(repository);
  }

  @Bean
  public DataSource dataSource() {

    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder.setType(EmbeddedDatabaseType.H2).build();
  }

  @Bean
  public EntityManagerFactory testEntityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("org.up.finance.entity");
    factory.setDataSource(dataSource());
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  @Bean
  public PlatformTransactionManager testTransactionManager() {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(testEntityManagerFactory());
    return txManager;
  }

  @Bean
  public JavaMailSender emailSender() {
    return new JavaMailSenderImpl();
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
  public UserService userService(UserRepository repository) {
    return new UserServiceImpl(repository);
  }

  @Bean
  public SecurityUtil securityUtilTest() {
    return new SecurityUtilTestImpl();
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
  public WalletService walletService(WalletRepository repository) {
    return new WalletServiceImpl(repository);
  }

  @Bean
  public DailyNoteService dailyNoteService(DailyNoteRepository dailyNoteRepository) {
    return new DailyNoteServiceImpl(dailyNoteRepository);
  }

  @Bean
  public TransactionService transactionService(TransactionRepository repository) {
    return new TransactionServiceImpl(repository);
  }
}
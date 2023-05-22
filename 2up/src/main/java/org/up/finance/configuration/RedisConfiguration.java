package org.up.finance.configuration;

import org.ptit.okrs.core_redis.config.EnableCoreRedis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCoreRedis
public class RedisConfiguration {
  @Value("${spring.data.redis.host}")
  private String redisHost;
  @Value("${spring.data.redis.port}")
  private Integer redisPort;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    LettuceConnectionFactory lcf = new LettuceConnectionFactory();
    lcf.setHostName(redisHost);
    lcf.setPort(redisPort);
    lcf.afterPropertiesSet();
    return lcf;
  }

  @Bean
  public RedisTemplate<String, String> redisTemplateStr(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, String> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
  }
}

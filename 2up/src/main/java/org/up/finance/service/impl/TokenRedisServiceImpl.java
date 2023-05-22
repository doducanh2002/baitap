package org.up.finance.service.impl;

import org.ptit.okrs.core_redis.BaseRedisHashServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.up.finance.service.TokenRedisService;

public class TokenRedisServiceImpl extends BaseRedisHashServiceImpl<String> implements TokenRedisService {
  public TokenRedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
    super(redisTemplate);
  }
}

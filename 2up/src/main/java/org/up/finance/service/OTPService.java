package org.up.finance.service;

import org.ptit.okrs.core_redis.BaseRedisService;

public interface OTPService extends BaseRedisService<String> {
  boolean verify(String email, String otp);
}

package org.up.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_redis.BaseRedisServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.up.finance.exception.OTPBadRequestException;
import org.up.finance.exception.OTPNotFoundException;
import org.up.finance.service.OTPService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OTPServiceImpl extends BaseRedisServiceImpl<String> implements OTPService {

  public OTPServiceImpl(
        RedisTemplate<String, Object> redisTemplate,
        long timeOut,
        TimeUnit unitTimeOut
  ) {
    super(redisTemplate, timeOut, unitTimeOut);
  }

  public boolean verify(String email, String otp) {
    log.info("(verify) email:{}, otp:{}", email, otp);
    var otpRedis = get(email);

    if (Objects.isNull(otpRedis)) {
      throw new OTPNotFoundException(otp, "OTP has expired!");
    } else if (!otpRedis.equals(otp)) {
      throw new OTPBadRequestException(otp, "OTP incorrect!");
    }
    return true;
  }

  @Override
  protected boolean isSavePersistent() {
    return false;
  }
}

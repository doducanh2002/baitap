package org.up.finance.security.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.up.finance.security.SecurityUtil;

import java.security.Principal;

@Component("securityUtilTest")
@Profile("dev-local")
@Primary
public class SecurityUtilTestImpl implements SecurityUtil {
    @Override
    public String getUserId() {
        return "userId";
    }

    @Override
    public String getEmail() {
        return "email";
    }
}

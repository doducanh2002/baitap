package org.up.finance.security.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.up.finance.security.SecurityUtil;

@Component
public class SecurityUtilImpl implements SecurityUtil {
    @Override
    public String getUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    }

    @Override
    public String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}

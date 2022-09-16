package org.aibles.library.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;

@Slf4j
public class HeaderLocalResolver  extends AcceptHeaderLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        List<LanguageRange> list = LanguageRange.parse(request.getHeader("Accept-Language"));
        log.info("Language Range : {}", list);
        Locale locale = Locale.lookup(list, LanguageConstants.LOCALES);
        return locale;
    }
}
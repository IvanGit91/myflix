package me.personal.myflix.utility.framework;

import jakarta.servlet.http.HttpServletRequest;
import me.personal.myflix.config.framework.application_context.ApplicationContextProvider;
import me.personal.myflix.utility.multilanguage.ICurrentLocaleResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

/**
 * Manage context information
 *
 */
public class ApplicationContextHolder {

    private static final Locale forcedLocale = null;

    private ApplicationContextHolder() {
        throw new IllegalStateException("Utility class");
    }

    public static Locale getCurrentLocale() {
        if (forcedLocale != null)
            return forcedLocale;
        return ApplicationContextProvider.getBean(ICurrentLocaleResolver.class).getCurrentLocale().get();
    }

    public static String getCurrentTimezone() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null)
            return "UTC";

        HttpServletRequest req = sra.getRequest();
        String reqTimezone = req.getHeader("timezone");
        if (reqTimezone == null)
            return "UTC";
        return reqTimezone;
    }
}

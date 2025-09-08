package me.personal.myflix.utility.multilanguage;

import java.util.Locale;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestAttributesLocaleResolver implements ICurrentLocaleResolver {

	@Override
	public Optional<Locale> getCurrentLocale() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		// es: system startup
		if (sra == null)
			return Optional.of(LocaleUtils.getItalianLocale());
		HttpServletRequest req = sra.getRequest();
		return Optional.of(req.getLocale());
	}

}

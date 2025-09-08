package me.personal.myflix.config;

import me.personal.myflix.config.framework.annotation_resolver.EnumExposedService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
@EnableScheduling
public abstract class BaseWebConfiguration {

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		return loggingFilter;
	}

	@Bean
	public Formatter<LocalDate> localDateFormatter() {
		return new Formatter<>() {
			@Override
			public LocalDate parse(String text, Locale locale) throws ParseException {
				return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}

			@Override
			public String print(LocalDate object, Locale locale) {
				return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
			}
		};
	}

	@Bean
	public EnumExposedService getEnumExposedService() {
        EnumExposedService service = new EnumExposedService();
        service.init();
		return service;
	}
}

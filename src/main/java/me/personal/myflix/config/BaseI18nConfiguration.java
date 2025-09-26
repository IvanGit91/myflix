package me.personal.myflix.config;

import me.personal.myflix.utility.multilanguage.ICurrentLocaleResolver;
import me.personal.myflix.utility.multilanguage.RequestAttributesLocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class BaseI18nConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public ICurrentLocaleResolver currentLocaleResolver() {
        return new RequestAttributesLocaleResolver();
    }

}

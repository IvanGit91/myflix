package me.personal.myflix;

import me.personal.myflix.config.BaseI18nConfiguration;
import me.personal.myflix.config.BaseMainConfiguration;
import me.personal.myflix.config.BaseWebConfiguration;
import me.personal.myflix.config.framework.application_context.ApplicationContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Import({BaseMainConfiguration.class, BaseI18nConfiguration.class, BaseWebConfiguration.class})
public class MyFlixApplication {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        ApplicationContextProvider.setStartupViaMain(true);
        SpringApplication.run(MyFlixApplication.class, args);
    }
}


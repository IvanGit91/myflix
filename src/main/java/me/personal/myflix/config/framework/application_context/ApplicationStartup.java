package me.personal.myflix.config.framework.application_context;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.TimeZone;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    public static boolean startedUp = false;

    public ApplicationStartup() {
        // temporary workaround for a problem that validationMessages go on default on the VM language
        Locale.setDefault(Locale.ITALY);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        startedUp = true;
    }

}

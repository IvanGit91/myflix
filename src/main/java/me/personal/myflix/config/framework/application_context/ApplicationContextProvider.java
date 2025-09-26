package me.personal.myflix.config.framework.application_context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    public static Boolean startupViaMain = false;
    private static ApplicationContext context;
    @Autowired
    private ApplicationContext ctx;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context = ac;
    }

    public static Boolean isStartIngUpInDevEnvironment() {
        return (!ApplicationStartup.startedUp) && (isInDevEnvironment() || isInIntegrationEnvironment());
    }

    public static Boolean isProdEnvironment() {
        List<String> activeProfiles = Arrays.asList(context.getEnvironment().getActiveProfiles());
        return activeProfiles.contains("prod");
    }

    public static Boolean isQAEnvironment() {
        return Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("qa");
    }

    public static Boolean isQAIndevEnvironment() {
        return Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("qa-indev");
    }

    public static Boolean isIntegrationTestEnvironment() {
        return Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("integration_test");
    }

    public static Boolean isInDevEnvironment() {
        return Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("dev");
    }

    public static Boolean isInIntegrationEnvironment() {
        return Arrays.asList(context.getEnvironment().getActiveProfiles()).contains("integration");
    }

    public static String getRealEnvironmentName() {
        if (isProdEnvironment())
            return "prod";
        if (isQAEnvironment())
            return "qa";
        if (isInDevEnvironment())
            return "dev";
        if (isInIntegrationEnvironment())
            return "integration";
        throw new IllegalStateException("Environment not recognized:" + Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

    public static String getEnvironmentsAsString() {
        return Arrays.asList(context.getEnvironment().getActiveProfiles()).toString();
    }

    public static void setStartupViaMain(Boolean startupViaMain) {
        ApplicationContextProvider.startupViaMain = startupViaMain;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> T getBeanWithGenerics(Class<T> clazz, List<Class<?>> genericClasses) {
        Class[] clazzes = genericClasses.toArray(new Class[0]);
        String[] beans = context.getBeanNamesForType(ResolvableType.forClassWithGenerics(clazz, clazzes));
        return (T) context.getBean(beans[0]);
    }

    public static MessageSource getMessageSourceBean() {
        return (getBean(MessageSource.class));
    }

    @PostConstruct
    private void postConstruct() {
        context = this.ctx;
    }

}

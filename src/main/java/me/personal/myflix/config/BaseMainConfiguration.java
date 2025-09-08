package me.personal.myflix.config;

import me.personal.myflix.config.framework.application_context.ApplicationContextProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@Configuration
public class BaseMainConfiguration {

	@Bean
	public ApplicationContextProvider contextProvider() {
		return new ApplicationContextProvider();
	}

	// Solve the lazy loading problem
	@Bean
	public FilterRegistrationBean<OpenEntityManagerInViewFilter> registerOpenEntityManagerInViewFilterBean() {
		FilterRegistrationBean<OpenEntityManagerInViewFilter> registrationBean = new FilterRegistrationBean<>();
		OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
		registrationBean.setFilter(filter);
		registrationBean.setOrder(5);
		return registrationBean;
	}
}

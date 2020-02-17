package com.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebServiceSecurityFilterConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private WebServiceCustomSecurityFilter webServiceSecurityFilter;

	@Bean
	public FilterRegistrationBean webServiceSecurityFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(webServiceSecurityFilter);
		// registration.setOrder(1);
		registration.setEnabled(true);
		registration.addUrlPatterns("/api/*");
		return registration;
	}

}

package com.charity;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class DefaultView extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addViewController("/").setViewName("forward:/landing.xhtml");
		// registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
}

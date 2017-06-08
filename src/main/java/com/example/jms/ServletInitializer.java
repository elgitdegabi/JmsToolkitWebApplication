package com.example.jms;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Initializes the servlets for the Spring Boot Application.
 * @author Gabriel
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configures the servlets for the Spring Boot Application
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JmsToolkitWebApplication.class);
	}

}

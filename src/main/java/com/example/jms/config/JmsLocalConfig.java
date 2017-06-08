package com.example.jms.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class for JMS beans.
 * @author Gabriel
 *
 */
@Configuration
@ConfigurationProperties("com.exmaple.jms.config.activemq")
@Profile("default")
public class JmsLocalConfig {

	private String url;
	private String user;
	private String password;
	
	/**
	 * Creates a {@link ConnectionFactory} for your configured JMS provider.
	 * @return
	 */
	@Bean
	public ConnectionFactory createConnectionFactoryBean() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(url);
		connectionFactory.setUserName(ActiveMQConnection.DEFAULT_USER); // or connectionFactory.setUserName(user);
		connectionFactory.setPassword(ActiveMQConnection.DEFAULT_PASSWORD); // or connectionFactory.setPassword(password);
		return connectionFactory;
	}

	/**
	 * Gets the url.
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 * @param host
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the user.
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

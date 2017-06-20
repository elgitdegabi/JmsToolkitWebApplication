package com.example.jms.config;

import javax.jms.ConnectionFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;


public class JmsLocalConfigTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private JmsLocalConfig jmsLocalConfig = new JmsLocalConfig();
	
	@Before
	public void setUp() {
		jmsLocalConfig.setUrl("someUrl");
		jmsLocalConfig.setUser("someUser");
		jmsLocalConfig.setPassword("somePassword");
	}
	
	@Test
	public void createConnectionFactoryBeanWithValidArgumentsShouldReturnAConnectionFactoryBean() {
		ConnectionFactory result = jmsLocalConfig.createConnectionFactoryBean();
		assertThat(result, notNullValue());
	}

	@Test
	public void createConnectionFactoryBeanWithNullArgumentsShouldThrowNullPointerException() {
		thrown.expect(NullPointerException.class);
		jmsLocalConfig.setUrl(null);
		jmsLocalConfig.setUser(null);
		jmsLocalConfig.setPassword(null);
		
		jmsLocalConfig.createConnectionFactoryBean();
	}

	@Test
	public void createConnectionFactoryBeanWithNullUserAndPasswordShouldReturnAConnectionFactoryBean() {
		jmsLocalConfig.setUser(null);
		jmsLocalConfig.setPassword(null);
		
		ConnectionFactory result = jmsLocalConfig.createConnectionFactoryBean();
		assertThat(result, notNullValue());
	}

	@Test
	public void createConnectionFactoryBeanWithNullPasswordShouldReturnAConnectionFactoryBean() {
		jmsLocalConfig.setUser(null);
		jmsLocalConfig.setPassword(null);
		
		ConnectionFactory result = jmsLocalConfig.createConnectionFactoryBean();
		assertThat(result, notNullValue());
	}
	
	@Test
	public void setGetUrlWithValidArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUrl("someUrl");
		assertThat(jmsLocalConfig.getUrl(), notNullValue());
		assertThat(jmsLocalConfig.getUrl(), equalTo("someUrl"));
	}

	@Test
	public void setGetUrlWithNullArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUrl(null);
		assertThat(jmsLocalConfig.getUrl(), nullValue());
	}
	
	@Test
	public void setGetUrlWithEmptyArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUrl("");
		assertThat(jmsLocalConfig.getUrl(), notNullValue());
		assertThat(jmsLocalConfig.getUrl(), equalTo(""));
	}

	@Test
	public void setGetUrlWithBlankArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUrl(" ");
		assertThat(jmsLocalConfig.getUrl(), notNullValue());
		assertThat(jmsLocalConfig.getUrl(), equalTo(" "));
	}

	@Test
	public void setGetUserWithValidArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUser("someUser");
		assertThat(jmsLocalConfig.getUser(), notNullValue());
		assertThat(jmsLocalConfig.getUser(), equalTo("someUser"));
	}

	@Test
	public void setGetUserWithNullArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUser(null);
		assertThat(jmsLocalConfig.getUser(), nullValue());
	}
	
	@Test
	public void setGetUserWithEmptyArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUser("");
		assertThat(jmsLocalConfig.getUser(), notNullValue());
		assertThat(jmsLocalConfig.getUser(), equalTo(""));
	}

	@Test
	public void setGetUserWithBlankArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setUser(" ");
		assertThat(jmsLocalConfig.getUser(), notNullValue());
		assertThat(jmsLocalConfig.getUser(), equalTo(" "));
	}

	@Test
	public void setGetPasswordWithValidArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setPassword("somePassword");
		assertThat(jmsLocalConfig.getPassword(), notNullValue());
		assertThat(jmsLocalConfig.getPassword(), equalTo("somePassword"));
	}

	@Test
	public void setGetPasswordWithNullArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setPassword(null);
		assertThat(jmsLocalConfig.getPassword(), nullValue());
	}
	
	@Test
	public void setGetPasswordWithEmptyArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setPassword("");
		assertThat(jmsLocalConfig.getPassword(), notNullValue());
		assertThat(jmsLocalConfig.getPassword(), equalTo(""));
	}

	@Test
	public void setGetPasswordWithBlankArgumentsShouldTheSettedValue() {
		jmsLocalConfig.setPassword(" ");
		assertThat(jmsLocalConfig.getPassword(), notNullValue());
		assertThat(jmsLocalConfig.getPassword(), equalTo(" "));
	}	
}

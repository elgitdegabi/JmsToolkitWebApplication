package com.example.jms.enums;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;


public class ResourcesEnumTest {

	@Test
	public void resourcesEnumShouldReturnTheConfiguredValues() {
		assertThat(ResourcesEnum.QUEUE001.getType(), equalTo("Q"));
		assertThat(ResourcesEnum.QUEUE001.getCode(), equalTo("QUEUE_001"));
		assertThat(ResourcesEnum.QUEUE001.getName(), equalTo("QUEUE 001 in ActiveMQ"));
		assertThat(ResourcesEnum.QUEUE002.getType(), equalTo("Q"));
		assertThat(ResourcesEnum.QUEUE002.getCode(), equalTo("QUEUE_002"));
		assertThat(ResourcesEnum.QUEUE002.getName(), equalTo("QUEUE 002 in ActiveMQ"));
		assertThat(ResourcesEnum.TOPIC001.getType(), equalTo("T"));
		assertThat(ResourcesEnum.TOPIC001.getCode(), equalTo("TOPIC_001"));
		assertThat(ResourcesEnum.TOPIC001.getName(), equalTo("TOPIC 001 in ActiveMQ"));
		assertThat(ResourcesEnum.TOPIC002.getType(), equalTo("T"));
		assertThat(ResourcesEnum.TOPIC002.getCode(), equalTo("TOPIC_002"));
		assertThat(ResourcesEnum.TOPIC002.getName(), equalTo("TOPIC 002 in ActiveMQ"));
		assertThat(ResourcesEnum.TOPIC003.getType(), equalTo("T"));
		assertThat(ResourcesEnum.TOPIC003.getCode(), equalTo("TOPIC_003"));
		assertThat(ResourcesEnum.TOPIC003.getName(), equalTo("TOPIC 003 in ActiveMQ"));		
	}
	
	@Test
	public void getResourcesByCodeWithValidCodeShouldReturnTheProperResourcesEnum() {
		ResourcesEnum result = ResourcesEnum.getResourceByCode("TOPIC_001");
		assertThat(result, notNullValue());
		assertThat(result.getType(), equalTo("T"));
		assertThat(result.getCode(), equalTo("TOPIC_001"));
		assertThat(result.getName(), equalTo("TOPIC 001 in ActiveMQ"));
	}
	
	@Test
	public void getResourcesByCodeWithNoValidCodeShouldReturnANullResourcesEnum() {
		ResourcesEnum result = ResourcesEnum.getResourceByCode("someCode");
		assertThat(result, nullValue());
	}
	
	@Test
	public void getResourcesByCodeWithNullCodeShouldReturnANullResourcesEnum() {
		ResourcesEnum result = ResourcesEnum.getResourceByCode(null);
		assertThat(result, nullValue());
	}		
}

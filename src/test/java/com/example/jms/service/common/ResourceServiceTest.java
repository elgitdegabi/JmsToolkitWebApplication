package com.example.jms.service.common;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.jms.enums.ResourcesEnum;
import com.example.jms.service.common.ResourceService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class ResourceServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private ResourceService resourceService = new ResourceService();
	
	private Map<String, String> configuredResources;
	
	@Before
	public void setUp() {
		configuredResources = new HashMap<>();
		
		for (ResourcesEnum resource:ResourcesEnum.values()) {
			configuredResources.put(resource.getCode(), resource.getName());
		}
	}
	
	@Test
	public void getConfiguredResourcesWithValidArgumentsShouldReturnAResponseEntityWithListOfConfiguredResources() {
		Map<String,String> result = resourceService.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(ResourcesEnum.values().length));
	}
}

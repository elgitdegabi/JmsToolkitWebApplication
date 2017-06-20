package com.example.jms.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.jms.enums.ResourcesEnum;
import com.example.jms.service.common.ResourceService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class ResourceControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	ResourceService resourceServiceMock;
	@SpyBean // use inject mocks with spring boot versions lower than 1.4
	private ResourceController resourceController;
	
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
		Mockito.doReturn(configuredResources).when(resourceServiceMock).getConfiguredResources();
		
		ResponseEntity<Map<String,String>> result = resourceController.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(ResourcesEnum.values().length));
	}
	
	@Test
	public void getConfiguredResourcesWithValidArgumentsShouldReturnAResponseEntityWithNullListOfConfiguredResources() {
		Mockito.doReturn(null).when(resourceServiceMock).getConfiguredResources();
		
		ResponseEntity<Map<String,String>> result = resourceController.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), nullValue());
	}

	@Test
	public void getConfiguredResourcesWithValidArgumentsShouldReturnAResponseEntityWithEmptyListOfConfiguredResources() {
		Mockito.doReturn(new HashMap<>()).when(resourceServiceMock).getConfiguredResources();
		
		ResponseEntity<Map<String,String>> result = resourceController.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(0));
	}	
}

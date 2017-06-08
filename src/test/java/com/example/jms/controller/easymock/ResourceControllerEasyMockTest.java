package com.example.jms.controller.easymock;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jms.controller.ResourceController;
import com.example.jms.enums.ResourcesEnum;
import com.example.jms.service.common.ResourceService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(EasyMockRunner.class)
public class ResourceControllerEasyMockTest {

	@Mock
	ResourceService resourceServiceMock;

	@TestSubject
	private ResourceController resourceController = new ResourceController();
	
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
		EasyMock.expect(resourceServiceMock.getConfiguredResources()).andReturn(configuredResources);
		EasyMock.replay(resourceServiceMock);
		
		ResponseEntity<Map<String,String>> result = resourceController.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(ResourcesEnum.values().length));
	}
	
	@Test
	public void getConfiguredResourcesWithValidArgumentsShouldReturnAResponseEntityWithNullListOfConfiguredResources() {
		EasyMock.expect(resourceServiceMock.getConfiguredResources()).andReturn(null);
		EasyMock.replay(resourceServiceMock);
		
		ResponseEntity<Map<String,String>> result = resourceController.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), nullValue());
	}

	@Test
	public void getConfiguredResourcesWithValidArgumentsShouldReturnAResponseEntityWithEmptyListOfConfiguredResources() {
		EasyMock.expect(resourceServiceMock.getConfiguredResources()).andReturn(new HashMap<>());
		EasyMock.replay(resourceServiceMock);
		
		ResponseEntity<Map<String,String>> result = resourceController.getConfiguredResources();
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(0));
	}	
}

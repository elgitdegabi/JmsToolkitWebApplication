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

import com.example.jms.service.common.BrowseService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class BrowseControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	BrowseService browseServiceMock;
	
	@SpyBean // use inject mocks with spring boot versions lower than 1.4
	private BrowseController browseController;
	
	private Map<String, String> messages;
	
	@Before
	public void setUp() {
		messages = new HashMap<>();
		messages.put("1", "message 1");
		messages.put("2", "message 2");
		messages.put("3", "message 3");
	}
	
	@Test
	public void browseInWithValidArgumentsShouldReturnAResponseEntityWithStatusOKAndAMapOfMessages() {
		Mockito.doReturn(messages).when(browseServiceMock).getMessagesFrom(Mockito.anyString());
		
		ResponseEntity<Map<String, String>> result = browseController.browseIn("someResource");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(messages.size()));
	}
	
	@Test
	public void browseInWithNullArgumentsShouldReturnAResponseEntityWithStatusOKAndAMapOfMessages() {
		Mockito.doReturn(messages).when(browseServiceMock).getMessagesFrom(Mockito.anyString());
		
		ResponseEntity<Map<String, String>> result = browseController.browseIn(null);
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(messages.size()));
	}

	@Test
	public void browseInWithEmptyArgumentsShouldReturnAResponseEntityWithStatusOKAndAMapOfMessages() {
		Mockito.doReturn(messages).when(browseServiceMock).getMessagesFrom(Mockito.anyString());
		
		ResponseEntity<Map<String, String>> result = browseController.browseIn("");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(messages.size()));
	}

	@Test
	public void browseInWithBlankArgumentsShouldReturnAResponseEntityWithStatusOKAndAMapOfMessages() {
		Mockito.doReturn(messages).when(browseServiceMock).getMessagesFrom(Mockito.anyString());
		
		ResponseEntity<Map<String, String>> result = browseController.browseIn(" ");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody().size(), equalTo(messages.size()));
	}	
}

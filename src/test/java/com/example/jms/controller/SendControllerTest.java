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

import com.example.jms.service.common.SendService;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class SendControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	SendService sendServiceMock;
	@SpyBean // use inject mocks with spring boot versions lower than 1.4
	private SendController sendController;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void sendMessageWithValidArgumentsShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage("someResource", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}
	
	@Test
	public void sendMessageWithNullResourceShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage(null, "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithNullMessageShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage("someResource", null);
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithEmptyResourceShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage("", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithEmptyMessageShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage("someResource", "");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithBlankResourceShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage(" ", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithBlankMessageShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(sendServiceMock).sendMessageTo(Mockito.anyString(), Mockito.anyString());
		
		ResponseEntity<Boolean> result = sendController.sendMessage("someResource", " ");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}
}

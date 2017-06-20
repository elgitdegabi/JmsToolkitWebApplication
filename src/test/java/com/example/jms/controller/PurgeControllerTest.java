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

import com.example.jms.service.common.PurgeService;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class PurgeControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	PurgeService purgeServiceMock;
	
	@SpyBean // use inject mocks with spring boot versions lower than 1.4
	private PurgeController purgeController;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void sendMessageWithValidArgumentsShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(purgeServiceMock).purgeMessages(Mockito.anyString());
		
		ResponseEntity<Boolean> result = purgeController.purgeMessages("someResource");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}
	
	@Test
	public void sendMessageWithNullArgumentsShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(purgeServiceMock).purgeMessages(Mockito.anyString());
		
		ResponseEntity<Boolean> result = purgeController.purgeMessages(null);
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithEmptyArgumentsShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(purgeServiceMock).purgeMessages(Mockito.anyString());
		
		ResponseEntity<Boolean> result = purgeController.purgeMessages("");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}

	@Test
	public void sendMessageWithBlankArgumentsShouldReturnAResponseEntityWithStatusOK() {
		Mockito.doReturn(true).when(purgeServiceMock).purgeMessages(Mockito.anyString());
		
		ResponseEntity<Boolean> result = purgeController.purgeMessages(" ");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		assertThat(result.getBody(), equalTo(true));
	}	
}

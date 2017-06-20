package com.example.jms.service.common;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.jms.service.queue.QueueService;
import com.example.jms.service.topic.TopicService;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class SendServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	private QueueService queueServiceMock;

	@MockBean
	private TopicService topicServiceMock;
	
	@SpyBean
	private SendService sendService;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void sendMessageToWithValidQueueArgumentsShouldReturnATrueResponse() {
		Mockito.doReturn(true).when(queueServiceMock).sendMessageTo("QUEUE_001", "someMessage");
		boolean result = sendService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidTopicArgumentsShouldReturnATrueResponse() {
		Mockito.doReturn(true).when(topicServiceMock).sendMessageTo("TOPIC_001", "someMessage");
		boolean result = sendService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithNonValidQueueArgumentsShouldReturnAFalseResponse() {
		boolean result = sendService.sendMessageTo("QUEUE_NNN", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
	@Test
	public void sendMessageToWithNonValidTopicArgumentsShouldReturnAFalseResponse() {
		boolean result = sendService.sendMessageTo("TOPIC_NNN", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
	@Test
	public void sendMessageToWithNullQueueShouldReturnAFalseResponse() {
		boolean result = sendService.sendMessageTo(null, "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
	@Test
	public void sendMessageToWithNullTopicShouldReturnAFalseResponse() {
		boolean result = sendService.sendMessageTo(null, "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}	
	
}

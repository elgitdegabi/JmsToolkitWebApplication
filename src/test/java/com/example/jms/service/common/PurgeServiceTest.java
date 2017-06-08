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
public class PurgeServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	private QueueService queueServiceMock;

	@MockBean
	private TopicService topicServiceMock;
	
	@SpyBean
	private PurgeService purgeService;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void purgeMessagesWithValidQueueArgumentsShouldReturnATrueResponse() {
		Mockito.doReturn(true).when(queueServiceMock).removeMessagesFrom("QUEUE_001");
		boolean result = purgeService.purgeMessages("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void purgeMessagesWithValidTopicArgumentsShouldReturnATrueResponse() {
		Mockito.doReturn(true).when(topicServiceMock).removeMessagesBySubscriberFrom("TOPIC_001");
		boolean result = purgeService.purgeMessages("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void purgeMessagesWithNonValidQueueArgumentsShouldReturnAFalseResponse() {
		boolean result = purgeService.purgeMessages("QUEUE_NNN");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
	@Test
	public void purgeMessagesWithNonValidTopicArgumentsShouldReturnAFalseResponse() {
		boolean result = purgeService.purgeMessages("TOPIC_NNN");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
	@Test
	public void purgeMessagesWithNullQueueShouldReturnAFalseResponse() {
		boolean result = purgeService.purgeMessages(null);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
	@Test
	public void purgeMessagesWithNullTopicShouldReturnAFalseResponse() {
		boolean result = purgeService.purgeMessages(null);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}	
	
}

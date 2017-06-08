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

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
public class BrowseServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@MockBean
	private QueueService queueServiceMock;

	@MockBean
	private TopicService topicServiceMock;
	
	@SpyBean
	private BrowseService browseService;
	
	private Map<String,String> messages;
	
	@Before
	public void setUp() {
		messages = new HashMap<>();
		messages.put("1", "message 1");
		messages.put("2", "message 2");
		messages.put("3", "message 3");
	}
	
	@Test
	public void getMessagesFromWithValidQueueArgumentsShouldReturnATrueResponse() {
		Mockito.doReturn(messages).when(queueServiceMock).browseMessagesFrom("QUEUE_001");
		Map<String,String> result = browseService.getMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(messages.size()));
	}
	
	@Test
	public void getMessagesFromWithValidTopicArgumentsShouldReturnATrueResponse() {
		Mockito.doReturn(messages).when(topicServiceMock).browseMessagesFrom("TOPIC_001");
		Map<String,String> result = browseService.getMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(messages.size()));
	}

	@Test
	public void getMessagesFromWithNonValidQueueArgumentsShouldReturnAFalseResponse() {
		Map<String,String> result = browseService.getMessagesFrom("QUEUE_NNN");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void getMessagesFromWithNonValidTopicArgumentsShouldReturnAFalseResponse() {
		Map<String,String> result = browseService.getMessagesFrom("TOPIC_NNN");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void getMessagesFromWithNullQueueShouldReturnAFalseResponse() {
		Map<String,String> result = browseService.getMessagesFrom(null);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void getMessagesFromWithNullTopicShouldReturnAFalseResponse() {
		Map<String,String> result = browseService.getMessagesFrom(null);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}
}

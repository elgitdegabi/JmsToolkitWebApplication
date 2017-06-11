package com.example.jms.service.topic.easymock;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jms.service.topic.TopicService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(EasyMockRunner.class)
public class TopicServiceEasyMockTest {

	@TestSubject
	private TopicService topicService = new TopicService();
	
	@Mock
	private ConnectionFactory connectionFactoryMock;
	@Mock
	private Connection connectionMock;
	@Mock
	private Session sessionMock;
	@Mock
	private Topic topicDestinationMock;
	@Mock
	private MessageProducer messageProducerMock;
	@Mock
	private TopicSubscriber messageConsumerMock;
	@Mock
	private TextMessage textMessageMock;
	
	private Map<String,String> expectedMessages;
	private Vector<Message> topicMessages = new Vector<>();
	
	@Before
	public void setUp() throws Exception {
		createExpectedMessages();
		createTopicMessages();
		//initTopicSubscribers();
	}

	private void createExpectedMessages() {
		expectedMessages = new HashMap<>();
		expectedMessages.put("1", "topic message 1");
		expectedMessages.put("2", "topic message 2");
		expectedMessages.put("3", "topic message 3");		
	}
	
	private void createTopicMessages() throws Exception {
		TextMessage message1Mock = EasyMock.mock(TextMessage.class);
		EasyMock.expect(message1Mock.getText()).andReturn(expectedMessages.get("1"));
		topicMessages.add(message1Mock);
		
		TextMessage message2Mock = EasyMock.mock(TextMessage.class);
		EasyMock.expect(message2Mock.getText()).andReturn(expectedMessages.get("2"));
		topicMessages.add(message2Mock);

		TextMessage message3Mock = EasyMock.mock(TextMessage.class);
		EasyMock.expect(message3Mock.getText()).andReturn(expectedMessages.get("3"));
		topicMessages.add(message3Mock);
		
		EasyMock.replay(message1Mock, message2Mock, message3Mock);
	}

	private void initTopicSubscribers() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createDurableSubscriber(EasyMock.anyObject(Topic.class), EasyMock.anyString())).andReturn(messageConsumerMock);
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
				
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageConsumerMock);
		
		topicService.initTopicSubscribers();
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionAndConnectionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		connectionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSendMessageThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateTextMessageThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateProducerThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andReturn(topicDestinationMock);
		EasyMock.expect(sessionMock.createProducer(topicDestinationMock)).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateTopicThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createTopic(EasyMock.anyString())).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
				
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateSessionThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
				
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSetClientIDThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
				
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateConnectionThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, topicDestinationMock, messageProducerMock, textMessageMock);
				
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
}

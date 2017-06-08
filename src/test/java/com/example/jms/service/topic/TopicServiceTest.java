package com.example.jms.service.topic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
public class TopicServiceTest {

	@MockBean
	private ConnectionFactory connectionFactoryMock;

	@SpyBean
	private TopicService topicService;
	
	private Map<String,String> expectedMessages;
	private Vector<Message> topicMessages = new Vector<>();
	
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
	
	@Before
	public void setUp() throws Exception {
		createExpectedMessages();
		createTopicMessages();
		initTopicSubscribers();
	}

	private void createExpectedMessages() {
		expectedMessages = new HashMap<>();
		expectedMessages.put("1", "topic message 1");
		expectedMessages.put("2", "topic message 2");
		expectedMessages.put("3", "topic message 3");		
	}
	
	private void createTopicMessages() throws Exception {
		TextMessage message1Mock = Mockito.mock(TextMessage.class);
		Mockito.doReturn(expectedMessages.get("1")).when(message1Mock).getText();
		topicMessages.add(message1Mock);
		
		TextMessage message2Mock = Mockito.mock(TextMessage.class);
		topicMessages.add(message2Mock);
		Mockito.doReturn(expectedMessages.get("2")).when(message2Mock).getText();

		TextMessage message3Mock = Mockito.mock(TextMessage.class);
		Mockito.doReturn(expectedMessages.get("3")).when(message3Mock).getText();
		topicMessages.add(message3Mock);		
	}
	
	private void initTopicSubscribers() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(Mockito.any(Topic.class), Mockito.anyString());
		topicService.initTopicSubscribers();
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doThrow(Exception.class).when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionAndConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doThrow(Exception.class).when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSendMessageThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doThrow(Exception.class).when(messageProducerMock).send(textMessageMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateTextMessageThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doThrow(Exception.class).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateProducerThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doThrow(Exception.class).when(sessionMock).createProducer(topicDestinationMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateQueueThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doThrow(Exception.class).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateSessionThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doThrow(Exception.class).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSetClientIDThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doThrow(Exception.class).when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateConnectionThrowsAnException() throws Exception {
		Mockito.doThrow(Exception.class).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = topicService.sendMessageTo("TOPIC_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAMapOfMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(topicMessages.get(0))
			.doReturn(topicMessages.get(1))
			.doReturn(topicMessages.get(2))
			.doReturn(null)
			.when(messageConsumerMock)
			.receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(expectedMessages.size()));
		assertThat(result.get("1"), equalTo(expectedMessages.get("1")));
		assertThat(result.get("2"), equalTo(expectedMessages.get("2")));
		assertThat(result.get("3"), equalTo(expectedMessages.get("3")));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenConsumeZeoMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenNotFoundMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenConsumerMessageCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doThrow(Exception.class).when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}
	
	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenSessionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doThrow(Exception.class).when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenReceiveThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doThrow(JMSException.class).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateTopicThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doThrow(JMSException.class).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSubscriberTOPIC_001");
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateQueueThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doThrow(JMSException.class).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateSessionThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doThrow(JMSException.class).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenSetClientIdThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doThrow(JMSException.class).when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateConnectionThrowsAnException() throws Exception {
		Mockito.doThrow(JMSException.class).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = topicService.browseMessagesFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(topicMessages.get(0)).doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnATrueResponseWhenNotReceiveMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnATrueResponseWhenMessageConsumerCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doThrow(JMSException.class).when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doThrow(JMSException.class).when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(JMSException.class).when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnAFalseResponseWhenReceiveThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(topicDestinationMock).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createDurableSubscriber(topicDestinationMock, "theSuscriberTOPIC_001");
		Mockito.doThrow(JMSException.class).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateTopicThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doThrow(JMSException.class).when(sessionMock).createTopic(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateSessionThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doThrow(JMSException.class).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnAFalseResponseWhenSetClientIDThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doThrow(JMSException.class).when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesBySubscriberFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateConnectionThrowsAnException() throws Exception {
		Mockito.doThrow(JMSException.class).when(connectionFactoryMock).createConnection();

		boolean result = topicService.removeMessagesBySubscriberFrom("TOPIC_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}	
}

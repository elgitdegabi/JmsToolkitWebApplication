package com.example.jms.service.queue.easymock;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jms.service.queue.QueueService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(EasyMockRunner.class)
public class QueueServiceEasyMockTest {

	@TestSubject
	private QueueService queueService = new QueueService();
	
	@Mock
	private ConnectionFactory connectionFactoryMock;
	@Mock
	private Connection connectionMock;
	@Mock
	private Session sessionMock;
	@Mock
	private Queue queueDestinationMock;
	@Mock
	private QueueBrowser queueBrowserMock;
	@Mock
	private MessageProducer messageProducerMock;
	@Mock
	private MessageConsumer messageConsumerMock;
	@Mock
	private TextMessage textMessageMock;
	
	private Map<String,String> expectedMessages;
	private Vector<Message> queueMessages = new Vector<>();
	
	@Before
	public void setUp() throws Exception {
		expectedMessages = new HashMap<>();
		expectedMessages.put("1", "queue message 1");
		expectedMessages.put("2", "queue message 2");
		expectedMessages.put("3", "queue message 3");
		
		TextMessage message1Mock = EasyMock.mock(TextMessage.class);
		EasyMock.expect(message1Mock.getText()).andReturn(expectedMessages.get("1"));
		queueMessages.add(message1Mock);
		
		TextMessage message2Mock = EasyMock.mock(TextMessage.class);
		EasyMock.expect(message2Mock.getText()).andReturn(expectedMessages.get("2"));
		queueMessages.add(message2Mock);

		TextMessage message3Mock = EasyMock.mock(TextMessage.class);
		EasyMock.expect(message3Mock.getText()).andReturn(expectedMessages.get("3"));
		queueMessages.add(message3Mock);
		
		EasyMock.replay(message1Mock, message2Mock, message3Mock);
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);		
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();		
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();		
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));;
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();		
		sessionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionAndConnectionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall();		
		sessionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));;
		connectionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSendMessageThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andReturn(textMessageMock);
		messageProducerMock.send(textMessageMock);
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));		
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock, textMessageMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateTextMessageThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andReturn(messageProducerMock);
		EasyMock.expect(sessionMock.createTextMessage(EasyMock.anyString())).andThrow(new JMSException("Some JMS Exception"));		
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageProducerMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateProducerThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createProducer(queueDestinationMock)).andThrow(new JMSException("Some JMS Exception"));		
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateQueueThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andThrow(new JMSException("Some JMS Exception"));		
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock);

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
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock);

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
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock, connectionMock);

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
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");

		EasyMock.verify(connectionFactoryMock);

		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAMapOfMessages() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andReturn(queueBrowserMock);
		EasyMock.expect(queueBrowserMock.getEnumeration()).andReturn(queueMessages.elements());
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(expectedMessages.size()));
		assertThat(result.get("1"), equalTo(expectedMessages.get("1")));
		assertThat(result.get("2"), equalTo(expectedMessages.get("2")));
		assertThat(result.get("3"), equalTo(expectedMessages.get("3")));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenConsumeZeoMessages() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andReturn(queueBrowserMock);
		EasyMock.expect(queueBrowserMock.getEnumeration()).andReturn(Collections.emptyEnumeration());
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenNotFoundMessages() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andReturn(queueBrowserMock);
		EasyMock.expect(queueBrowserMock.getEnumeration()).andReturn(null);
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenSessionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andReturn(queueBrowserMock);
		EasyMock.expect(queueBrowserMock.getEnumeration()).andReturn(Collections.emptyEnumeration());
		sessionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenConnectionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andReturn(queueBrowserMock);
		EasyMock.expect(queueBrowserMock.getEnumeration()).andReturn(Collections.emptyEnumeration());
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenGetEnumeratoinThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andReturn(queueBrowserMock);
		EasyMock.expect(queueBrowserMock.getEnumeration()).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, queueBrowserMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateBrowserThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createBrowser(queueDestinationMock)).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateQueueThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateSessionThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenSetClientIdThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateConnectionThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock);
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andReturn(messageConsumerMock);
		EasyMock.expect(messageConsumerMock.receive(EasyMock.anyLong())).andReturn(queueMessages.get(0)).andReturn(null);
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenNotReceiveMessages() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andReturn(messageConsumerMock);
		EasyMock.expect(messageConsumerMock.receive(EasyMock.anyLong())).andReturn(null);
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenMessageConsumerCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andReturn(messageConsumerMock);
		EasyMock.expect(messageConsumerMock.receive(EasyMock.anyLong())).andReturn(null);
		messageConsumerMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andReturn(messageConsumerMock);
		EasyMock.expect(messageConsumerMock.receive(EasyMock.anyLong())).andReturn(null);
		messageConsumerMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		sessionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andReturn(messageConsumerMock);
		EasyMock.expect(messageConsumerMock.receive(EasyMock.anyLong())).andReturn(null);
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenReceiveThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andReturn(messageConsumerMock);
		EasyMock.expect(messageConsumerMock.receive(EasyMock.anyLong())).andThrow(new JMSException("Some JMS Exception"));
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateConsumerThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andReturn(queueDestinationMock);
		EasyMock.expect(sessionMock.createConsumer(queueDestinationMock)).andThrow(new JMSException("Some JMS Exception"));
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock, queueDestinationMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateQueueThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andReturn(sessionMock);
		EasyMock.expect(sessionMock.createQueue(EasyMock.anyString())).andThrow(new JMSException("Some JMS Exception"));
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock, sessionMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateSessionThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall();
		connectionMock.start();
		EasyMock.expectLastCall();
		EasyMock.expect(connectionMock.createSession(EasyMock.anyBoolean(), EasyMock.anyInt())).andThrow(new JMSException("Some JMS Exception"));
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenSetClientIDThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andReturn(connectionMock);
		connectionMock.setClientID(EasyMock.anyString());
		EasyMock.expectLastCall().andThrow(new JMSException("Some JMS Exception"));
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock, connectionMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateConnectionThrowsAnException() throws Exception {
		EasyMock.expect(connectionFactoryMock.createConnection()).andThrow(new JMSException("Some JMS Exception"));
		messageConsumerMock.close();
		EasyMock.expectLastCall();
		sessionMock.close();
		EasyMock.expectLastCall();
		connectionMock.close();
		EasyMock.expectLastCall();
		
		EasyMock.replay(connectionFactoryMock, connectionMock, sessionMock, messageConsumerMock);
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		EasyMock.verify(connectionFactoryMock);
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
}

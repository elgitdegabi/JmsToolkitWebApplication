package com.example.jms.service.queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
public class QueueServiceTest {

	@MockBean
	private ConnectionFactory connectionFactoryMock;

	@SpyBean
	private QueueService queueService;
	
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
		
		TextMessage message1Mock = Mockito.mock(TextMessage.class);
		Mockito.doReturn(expectedMessages.get("1")).when(message1Mock).getText();
		queueMessages.add(message1Mock);
		
		TextMessage message2Mock = Mockito.mock(TextMessage.class);
		Mockito.doReturn(expectedMessages.get("2")).when(message2Mock).getText();
		queueMessages.add(message2Mock);

		TextMessage message3Mock = Mockito.mock(TextMessage.class);
		Mockito.doReturn(expectedMessages.get("3")).when(message3Mock).getText();
		queueMessages.add(message3Mock);		
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doThrow(Exception.class).when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSessionAndConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(messageProducerMock).send(textMessageMock);
		Mockito.doThrow(Exception.class).when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}
	
	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSendMessageThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doReturn(textMessageMock).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doThrow(Exception.class).when(messageProducerMock).send(textMessageMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateTextMessageThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageProducerMock).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doThrow(Exception.class).when(sessionMock).createTextMessage(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateProducerThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doThrow(Exception.class).when(sessionMock).createProducer(queueDestinationMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
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
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
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
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenSetClientIDThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doThrow(Exception.class).when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void sendMessageToWithValidArgumentsShouldReturnATrueResponseWhenCreateConnectionThrowsAnException() throws Exception {
		Mockito.doThrow(Exception.class).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		boolean result = queueService.sendMessageTo("QUEUE_001", "someMessage");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAMapOfMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(queueBrowserMock).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doReturn(queueMessages.elements()).when(queueBrowserMock).getEnumeration();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
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
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(queueBrowserMock).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doReturn(Collections.emptyEnumeration()).when(queueBrowserMock).getEnumeration();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenNotFoundMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(queueBrowserMock).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doReturn(null).when(queueBrowserMock).getEnumeration();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenSessionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(queueBrowserMock).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doReturn(Collections.emptyEnumeration()).when(queueBrowserMock).getEnumeration();
		Mockito.doThrow(Exception.class).when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(queueBrowserMock).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doReturn(Collections.emptyEnumeration()).when(queueBrowserMock).getEnumeration();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(Exception.class).when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenGetEnumeratoinThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(queueBrowserMock).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doThrow(JMSException.class).when(queueBrowserMock).getEnumeration();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateBrowserThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doThrow(JMSException.class).when(sessionMock).createBrowser(queueDestinationMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
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
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
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
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenSetClientIdThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doThrow(JMSException.class).when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void browserMessagesFromWithValidArgumentsShouldReturnAnEmptyMapOfMessagesWhenCreateConnectionThrowsAnException() throws Exception {
		Mockito.doThrow(JMSException.class).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		Map<String,String> result = queueService.browseMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(0));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponse() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doReturn(queueMessages.get(0)).doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenNotReceiveMessages() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenMessageConsumerCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doThrow(JMSException.class).when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenSessionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doThrow(JMSException.class).when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnATrueResponseWhenConnectionCloseThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doReturn(null).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doThrow(JMSException.class).when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(true));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenReceiveThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doThrow(JMSException.class).when(messageConsumerMock).receive(Mockito.anyLong());
		Mockito.doNothing().when(messageConsumerMock).close();
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateConsumerThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doReturn(queueDestinationMock).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doThrow(JMSException.class).doReturn(messageConsumerMock).when(sessionMock).createConsumer(queueDestinationMock);
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateQueueThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doReturn(sessionMock).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doThrow(JMSException.class).when(sessionMock).createQueue(Mockito.anyString());
		Mockito.doNothing().when(sessionMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateSessionThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doNothing().when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doThrow(JMSException.class).when(connectionMock).createSession(Mockito.anyBoolean(), Mockito.anyInt());
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenSetClientIDThrowsAnException() throws Exception {
		Mockito.doReturn(connectionMock).when(connectionFactoryMock).createConnection();
		Mockito.doThrow(JMSException.class).when(connectionMock).setClientID(Mockito.anyString());
		Mockito.doNothing().when(connectionMock).close();
		
		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}

	@Test
	public void removeMessagesFromWithValidArgumentsShouldReturnAFalseResponseWhenCreateConnectionThrowsAnException() throws Exception {
		Mockito.doThrow(JMSException.class).when(connectionFactoryMock).createConnection();

		boolean result = queueService.removeMessagesFrom("QUEUE_001");
		
		assertThat(result, notNullValue());
		assertThat(result, equalTo(false));
	}
	
}

package com.example.jms.service.queue;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Services for JMS queues.
 * @author Gabriel
 *
 */
@Service
public class QueueService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);
	
	private static final int RECEIVE_TIME_OUT_MILISECONDS = 5000;
	
	@Autowired
	ConnectionFactory connectionFactory;

	/**
	 * Sends the given message to the given queue code.
	 * @param queueDestinationName
	 * @param message
	 * @return
	 */
	public boolean sendMessageTo(final String queueDestinationName, final String message) {
		LOGGER.info("sendMessageTo - start");
		
		Connection connection = null;
		Session session = null;

		try {
			connection = connectionFactory.createConnection();
			connection.setClientID("SendQueueSample");

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueDestinationName);

			session.createProducer(destination).send(session.createTextMessage(message));
			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR : ", e);
			return false;
		} finally {
			closeSession(session);
			closeConnection(connection);
			LOGGER.info("sendMessageTo - end");
		}
	}

	/**
	 * Browses and gets the messages from the given queue resource.
	 * @param queueDestinationName
	 * @return
	 */
	public Map<String, String> browseMessagesFrom(final String queueDestinationName) {
		LOGGER.info("browseMessagesFrom - start");
		
		Map<String, String> queueMessages = new HashMap<>();
		Connection connection = null;
		Session session = null;
		
		try {
			connection = connectionFactory.createConnection();
			connection.setClientID("BrowseQueueSample");
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue destination = session.createQueue(queueDestinationName);

			int elements = 1;

			@SuppressWarnings("unchecked")
			Enumeration<Message> messages = session.createBrowser(destination).getEnumeration();

			while (messages != null && messages.hasMoreElements()) {
				Message message = messages.nextElement();
				String textMessage = ((TextMessage) message).getText();
				queueMessages.put(String.valueOf(elements++), textMessage);
				
				LOGGER.debug("browseMessagesFrom - message : {}", textMessage);
			}
			
			return queueMessages;
		} catch (Exception e) {
			LOGGER.error("ERROR : ", e);
			return queueMessages;
		} finally {
			closeSession(session);
			closeConnection(connection);
			LOGGER.info("browseMessagesFrom - end");
		}
	}

	/**
	 * Removes/Consumes the messages from a given queue resource.
     * Only for test purpose: this feature will be executed through the queue's administrator console.
	 * @param queueDestinationName
	 * @return
	 */
	public boolean removeMessagesFrom(final String queueDestinationName) {
		LOGGER.info("removeMessagesFrom - start");
		
		Connection connection = null;
		Session session = null;
		MessageConsumer messageConsumer = null; 
				
		try {
			connection = connectionFactory.createConnection();
			connection.setClientID("ConsumeQueueSample");
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueDestinationName);

			messageConsumer = session.createConsumer(destination);
			Message receivedMessage = messageConsumer.receive(RECEIVE_TIME_OUT_MILISECONDS);

			while (receivedMessage != null) {
				receivedMessage = messageConsumer.receive(RECEIVE_TIME_OUT_MILISECONDS);
			}

			return true;
		} catch (Exception e) {
			LOGGER.error("EROR : ", e);
			return false;
		} finally {
			closeMessageConsumer(messageConsumer);
			closeSession(session);
			closeConnection(connection);
			LOGGER.info("removeMessagesFrom - end");
		}
	}
	
	/**
	 * Closes the given connection.
	 * @param connection
	 */
	private void closeConnection(final Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			LOGGER.warn("Error closing : ", e);
		}
	}
	
	/**
	 * Closes the given session.
	 * @param session
	 */
	private void closeSession(final Session session) {
		try {
			session.close();
		} catch (Exception e) {
			LOGGER.warn("Error closing : ", e);
		}		
	}
	
	/**
	 * Closes the given message consumer.
	 * @param messageConsumer
	 */
	private void closeMessageConsumer(final MessageConsumer messageConsumer) {
		try {
			messageConsumer.close();
		} catch (Exception e) {
			LOGGER.warn("Error closing : ", e);
		}		
	}
}

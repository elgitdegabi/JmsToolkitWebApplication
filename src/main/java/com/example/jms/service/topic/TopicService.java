package com.example.jms.service.topic;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jms.enums.ResourcesEnum;

/**
 * Services for topics.
 * @author Gabriel
 *
 */
@Service
public class TopicService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TopicService.class);
	
	private static final String SUBSCRIBER_NAME = "theSubscriber";
	private static final int RECEIVE_TIME_OUT_MILISECONDS = 5000;
	
	private Map<String,String> subscribers = new HashMap<>();
	
	@Autowired
	ConnectionFactory connectionFactory;

	/**
	 * Initializes the topic subscribers.
	 * IMPORTANT:
	 * If you don't create the subscribers through your topic administrator console before, the first time
	 * that you invoke the receive method you don't get any message because the subscriber is committed
	 * (and created) when you close the session.
	 * To avoid this, I create all the required subscribers here.
	 * If you don't need create subscribers in the application you should remove this method.
	 */
	@PostConstruct
	public void initTopicSubscribers() {
		for (ResourcesEnum resource : ResourcesEnum.values()) {
			if (resource.getType().equals(ResourcesEnum.TOPIC_TYPE)) {
				createTopicSubscriber(resource.getCode(), SUBSCRIBER_NAME + resource.getCode());
				createTopicSubscriber(resource.getCode(), SUBSCRIBER_NAME + resource.getCode()+ "_1"); // for test activemq purpose only
				createTopicSubscriber(resource.getCode(), SUBSCRIBER_NAME + resource.getCode()+ "_2"); // for test activemq purpose only
			}
		}
	}

	/**
	 * Creates a durable subscriber for the given subscriber name in the given topic resource..
	 * @param topicCode
	 * @param subscriberName
	 */
	private void createTopicSubscriber(final String topicCode, final String subscriberName) {
        Connection connection = null;
        Session session = null;
        MessageConsumer messageConsumer = null;
        
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(topicCode);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic(topicCode);            
        	messageConsumer = session.createDurableSubscriber(destination, subscriberName);
        	
        	subscribers.put(subscriberName, topicCode); // only to test the delete/remove from topic feature (see below)
        } catch (Exception e) {
        	LOGGER.error("ERROR", e);
        } finally {
        	closeMessageConsumer(messageConsumer);
            closeSesion(session);
            closeConnection(connection);                        
        }						
	}
	
	/**
	 * Sends the given message to the given topic resource. 
	 * @param topicDestinationName
	 * @param message
	 * @return
	 */
	public boolean sendMessageTo(final String topicDestinationName, final String message) {
        LOGGER.info("sendMessageTo - start");
        
        Connection connection = null;
        Session session = null;
    
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(topicDestinationName);
            
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(topicDestinationName);            
            
            session.createProducer(destination).send(session.createTextMessage(message));
            
            LOGGER.debug("sendMessageTo - message sent ok to : {}", topicDestinationName);
            LOGGER.debug("sendMessageTo - message sent as    : {}", SUBSCRIBER_NAME);
            LOGGER.debug("sendMessageTo - message body       : {}", message);
            
            return true;
        } catch (Exception e) {
        	LOGGER.error("ERROR", e);
            return false;
        } finally {
            closeSesion(session);
            closeConnection(connection);                        
            LOGGER.info("sendMessageTo - end");
        }
	}
	
	/**
	 * Browses and gets the messages from the given topic resource.
	 * @param topicDestinationName
	 * @return
	 */
    public Map<String, String> browseMessagesFrom(final String topicDestinationName) {
        LOGGER.info("browseMessagesFrom - start");

        Map<String, String> topicMessages = new HashMap<>();
        Connection connection = null;
        Session session = null;
        MessageConsumer messageConsumer = null;
        
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(topicDestinationName);
            connection.start(); // start the connection to receive messages
            
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Topic destination = session.createTopic(topicDestinationName);            

            // the subscriber was created previously by the topic admin or by the post construct method (see above)
        	messageConsumer = session.createDurableSubscriber(destination, SUBSCRIBER_NAME+topicDestinationName);
        	Message receivedMessage = messageConsumer.receive(RECEIVE_TIME_OUT_MILISECONDS);
            
        	LOGGER.debug("browseMessagesFrom - from topic : {}", topicDestinationName);
        	LOGGER.debug("browseMessagesFrom - subscriber : {}", (SUBSCRIBER_NAME+topicDestinationName));
        	
        	int elements = 1;
        	
            while(receivedMessage != null) {
            	String textMessage = ((TextMessage)receivedMessage).getText();
                topicMessages.put(String.valueOf(elements++), textMessage);
                
                LOGGER.debug("browseMessagesFrom - message    : {}", textMessage);
                
            	receivedMessage = messageConsumer.receive(RECEIVE_TIME_OUT_MILISECONDS);
            }
            
            return topicMessages;
        } catch (Exception e) {
            LOGGER.error("ERROR", e);
            return topicMessages;
        } finally {
        	closeMessageConsumer(messageConsumer);
            closeSesion(session);
            closeConnection(connection);                        
            LOGGER.info("browseMessagesFrom - end");
        }
    }
    
    /**
     * Removes/Consumes the messages from a given topic resource.
     * Only for test purpose: this feature will be executed through the topic's administrator console.
     * @param topicDestinationName
     * @return
     */
	public boolean removeMessagesBySubscriberFrom(final String topicDestinationName) {
		LOGGER.info("removeMessagesBySubscriberFrom - start");
		
        Connection connection = null;
        Session session = null;
        
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(topicDestinationName);
            connection.start();
            
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic(topicDestinationName);            

            for (String subscriber : subscribers.keySet()) {
            	if (subscribers.get(subscriber).equals(topicDestinationName)) {
                	LOGGER.debug("removeMessagesBySubscriberFrom - topic : {} - subscriber : {}", topicDestinationName, subscriber);
                	
		        	MessageConsumer messageConsumer = session.createDurableSubscriber(destination, subscriber);
		        	Message receivedMessage = messageConsumer.receive(RECEIVE_TIME_OUT_MILISECONDS);
		            
		            while(receivedMessage != null) {
		            	receivedMessage = messageConsumer.receive(RECEIVE_TIME_OUT_MILISECONDS);
		            }
		            
		            closeMessageConsumer(messageConsumer);
            	}
            }
            return true;
        } catch (Exception e) {
        	LOGGER.error("ERROR", e);
            return false;
        } finally {
            closeSesion(session);
            closeConnection(connection);  
            LOGGER.info("removeMessagesBySubscriberFrom - end");
        }
	}

	/**
	 * Closes the given connection.
	 * @param connection
	 */
	private void closeConnection(final Connection connection) {
		try {
			connection.close();
		} catch(Exception e) {
			LOGGER.warn("Error closing : ", e);
		}
	}
	
	/**
	 * Closes the given session.
	 * @param session
	 */
	private void closeSesion(final Session session) {
		try {
			session.close();
		} catch(Exception e) 
		{
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
    	} catch(Exception e) {
    		LOGGER.warn("Error closing : ", e);
    	}		
	}
}

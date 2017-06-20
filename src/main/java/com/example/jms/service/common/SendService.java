package com.example.jms.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jms.enums.ResourcesEnum;
import com.example.jms.service.queue.QueueService;
import com.example.jms.service.topic.TopicService;

/**
 * Services for the send actions.
 * @author Gabriel
 *
 */
@Service
public class SendService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendService.class);
	
	@Autowired
	QueueService queueService;
	
	@Autowired
	TopicService topicService;
	
	/**
	 * Sends the given message to the given resource codew.
	 * @param resourceCode
	 * @param message
	 * @return
	 */
	public boolean sendMessageTo(final String resourceCode, final String message){
		LOGGER.info("sendMessageTo - init");

		boolean result;
		ResourcesEnum resource = ResourcesEnum.getResourceByCode(resourceCode);

		if (resource != null) {
			if (resource.getType().equals(ResourcesEnum.QUEUE_TYPE)) {
				result = queueService.sendMessageTo(resource.getCode(), message);
			} else {
				result = topicService.sendMessageTo(resource.getCode(), message);
			} 
		} else {
			result = false;
		}
		
		LOGGER.info("sendMessageTo - end");
		return result;
	}
}

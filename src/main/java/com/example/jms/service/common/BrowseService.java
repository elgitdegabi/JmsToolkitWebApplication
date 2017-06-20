package com.example.jms.service.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jms.enums.ResourcesEnum;
import com.example.jms.service.queue.QueueService;
import com.example.jms.service.topic.TopicService;

/**
 * Services for the browse actions.
 * @author Gabriel
 *
 */
@Service
public class BrowseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrowseService.class);
	
	@Autowired
	QueueService queueService;
	
	@Autowired
	TopicService topicService;
	
	/**
	 * Gets the messages for the given resource.
	 * @param resourceCode
	 * @return
	 */
	public Map<String, String> getMessagesFrom(final String resourceCode) {
		LOGGER.info("getMessagesFrom - init");
		
		Map<String, String> messages = null;
		ResourcesEnum resource = ResourcesEnum.getResourceByCode(resourceCode);

		if (resource != null) {
			if (resource.getType().equals(ResourcesEnum.QUEUE_TYPE)) {
				messages = queueService.browseMessagesFrom(resourceCode);
			} else {
				messages = topicService.browseMessagesFrom(resourceCode);
			}
		} else {
			messages = new HashMap<>();
		}
		
		LOGGER.info("getMessagesFrom - end");
		return messages;
	}
}

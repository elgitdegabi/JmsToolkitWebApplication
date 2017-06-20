package com.example.jms.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jms.enums.ResourcesEnum;
import com.example.jms.service.queue.QueueService;
import com.example.jms.service.topic.TopicService;

/**
 * Services for the purge actions.
 * @author Gabriel
 *
 */
@Service
public class PurgeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurgeService.class);
	
	@Autowired
	QueueService queueService;
	
	@Autowired
	TopicService topicService;
	
	/**
	 * Purges the messages for a given resource code.
	 * @param resourceCode
	 * @return
	 */
	public boolean purgeMessages(final String resourceCode) {
		LOGGER.info("purgeMessages - init");

		boolean result;
		ResourcesEnum resource = ResourcesEnum.getResourceByCode(resourceCode);

		if (resource != null) {
			if (resource.getType().equals(ResourcesEnum.QUEUE_TYPE)) {
				result = queueService.removeMessagesFrom(resource.getCode());
			} else {
				result = topicService.removeMessagesBySubscriberFrom(resource.getCode());
			}
		} else {
			result = false;
		}
		
		LOGGER.info("purgeMessages - end");
		return result;
	}
}

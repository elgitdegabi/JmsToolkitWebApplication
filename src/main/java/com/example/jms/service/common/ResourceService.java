package com.example.jms.service.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.jms.enums.ResourcesEnum;

/**
 * Services for the resources.
 * @author Gabriel
 *
 */
@Service
public class ResourceService {
	
	/**
	 * Gets the list of configured resources for the application.
	 * @return
	 */
	public Map<String, String> getConfiguredResources() {
		Map<String, String> configuredResources = new HashMap<>();		
		
		for (ResourcesEnum resource:ResourcesEnum.values()) {
			configuredResources.put(resource.getCode(), resource.getName());
		}
		
		return configuredResources;
	}
}

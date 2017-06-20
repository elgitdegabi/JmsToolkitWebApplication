package com.example.jms.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jms.service.common.ResourceService;

/**
 * Handles the resource requests.
 * @author Gabriel
 *
 */
@RestController
public class ResourceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourcesService;
	
	/**
	 * Gets a list of configured resources (queues and/or topics) for the application.
	 * @return
	 */
	@RequestMapping(path="/resources/get", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,String>> getConfiguredResources() {
		LOGGER.info("getConfiguredResources - start");
		
		Map<String,String> configuredResources = resourcesService.getConfiguredResources();
		
		LOGGER.info("getConfiguredResources - end");
		return new ResponseEntity<Map<String,String>>(configuredResources, HttpStatus.OK);
	}
}

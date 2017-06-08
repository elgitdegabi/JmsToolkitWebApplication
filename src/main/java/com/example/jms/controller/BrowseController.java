package com.example.jms.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jms.service.common.BrowseService;

/**
 * Handles the browse requests.
 * @author Gabriel
 *
 */
@RestController
public class BrowseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrowseController.class);
	
	@Autowired
	BrowseService browseService;
	
	/**
	 * Gets a List of messages for the given resource.
	 * @param resource
	 * @return
	 */
	@RequestMapping(path="/browse/list", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,String>> browseIn(@RequestParam("resource")final String resource) {
		LOGGER.info("browseIn - start");
		LOGGER.debug("browseIn - resource : {}", resource);
		
		Map<String,String> messages = browseService.getMessagesFrom(resource);
		
		LOGGER.info("browseIn - end");
		return new ResponseEntity<Map<String,String>>(messages, HttpStatus.OK);
	}
}

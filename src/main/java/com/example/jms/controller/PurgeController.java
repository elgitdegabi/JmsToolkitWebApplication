package com.example.jms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jms.service.common.PurgeService;

/**
 * Handles the purge requests.
 * @author Gabriel
 *
 */
@RestController
public class PurgeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurgeController.class);
	
	@Autowired
	PurgeService purgeService;
	
	/**
	 * Purges/Consumes all the messages from the given resource.
	 * @param resource
	 * @return
	 */
	@RequestMapping(path="/purge/messages", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> purgeMessages(@RequestParam("resource")final String resource) {
		LOGGER.info("purgeMessages - start");
		LOGGER.debug("purgeMessages - resource : {}", resource);
		
		Boolean result = purgeService.purgeMessages(resource);
		
		LOGGER.info("purgeMessages - end");
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}

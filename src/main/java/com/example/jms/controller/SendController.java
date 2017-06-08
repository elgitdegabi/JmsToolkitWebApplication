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

import com.example.jms.service.common.SendService;

/**
 * Handles the send requests.
 * @author Gabriel
 *
 */
@RestController
public class SendController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendController.class);
	
	@Autowired
	private SendService sendService;
	
	/**
	 * Sends the given message to the given resource.
	 * @param resource
	 * @param message
	 * @return
	 */
	@RequestMapping(path="/send/message", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> sendMessage(
			@RequestParam("resource")final String resource, @RequestParam("message")final String message) {
		LOGGER.info("sendMessage - start");
		LOGGER.debug("sendMessage - resource: {}", resource);
		LOGGER.debug("sendMessage - message : {}", message);

		boolean status = sendService.sendMessageTo(resource, message);

		LOGGER.info("sendMessage - end");
		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}
}

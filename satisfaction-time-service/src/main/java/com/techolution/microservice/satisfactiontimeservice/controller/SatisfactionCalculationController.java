package com.techolution.microservice.satisfactiontimeservice.controller;

import java.io.File;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techolution.microservice.satisfactiontimeservice.dto.SatisfactionCalculationResponse;
import com.techolution.microservice.satisfactiontimeservice.service.SatisfactionCalculationService;

/**
 * @author kunal parnami
 *Controller acting as a invocation point of the service.
 */
@RestController
public class SatisfactionCalculationController {
	@Autowired
	private SatisfactionCalculationService satisfactionCalculationService;
	private SatisfactionCalculationResponse satisfactionCalculationResponse=null;
	static final Logger logger = LoggerFactory.getLogger(SatisfactionCalculationController.class);
	
	/*
	 * 
	 * Method for calculating optimized satisfaction in given time
	 */
	@GetMapping(value = "/getSatisfactionCount",produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public SatisfactionCalculationResponse getMaxSatisfaction()  {
		File file=null;
		Long statisfactionCount=0l;
		try {
			file = new File(SatisfactionCalculationController.class.getClassLoader().getResource("data.txt").toURI());
		} catch (URISyntaxException uriException) {
			logger.error("URI exception occured",uriException);
		}catch (Exception genericException) {
			logger.error("Exception occured",genericException);
		}
		statisfactionCount=satisfactionCalculationService.getSatisfactionValue(file);
		satisfactionCalculationResponse=new SatisfactionCalculationResponse();
		satisfactionCalculationResponse.setStatisfactionCount(statisfactionCount);
		return satisfactionCalculationResponse;
	}

}

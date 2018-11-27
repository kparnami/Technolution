package com.techolution.microservice.satisfactiontimeservice.service.impl;

import static java.util.Collections.reverseOrder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.techolution.microservice.satisfactiontimeservice.controller.SatisfactionCalculationController;
import com.techolution.microservice.satisfactiontimeservice.service.SatisfactionCalculationService;
@Service
public class SatisfactionCalculationServiceImpl implements SatisfactionCalculationService {
	static final Logger logger = LoggerFactory.getLogger(SatisfactionCalculationController.class);
	/*
	 * Method declaration optimized satisfaction in given time
	 */
	@Override
	public Long getSatisfactionValue(File file) {

		long totalTime = 0;
		long totalSatisfaction = 0;
		Map<Double, String> map = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			String timeDish[] = br.readLine().split(" ");
			totalTime = Long.parseLong(timeDish[0]);
			while ((line = br.readLine()) != null) {
				String arr[] = line.split(" ");
				double perSec = Double.parseDouble(arr[0]) / Double.parseDouble(arr[1]);
				map.put(perSec, line);
			}
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("Exception occured",fileNotFoundException);
		} catch (IOException ioException) {
			logger.error("Exception occured",ioException);
		}
		
		Map<Double, String> result = map.entrySet().stream().sorted(reverseOrder(Map.Entry.comparingByKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
		for (Map.Entry<Double, String> entry : result.entrySet()) {
			String arr[] = entry.getValue().split(" ");
			totalTime -= Long.parseLong(arr[1]);
			if (totalTime >= 0) {
				totalSatisfaction += Long.parseLong(arr[0]);
			} else {
				break;
			}

		}
		return totalSatisfaction;
	}

}

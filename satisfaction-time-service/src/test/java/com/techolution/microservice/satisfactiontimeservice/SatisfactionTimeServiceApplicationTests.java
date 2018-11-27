package com.techolution.microservice.satisfactiontimeservice;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.techolution.microservice.satisfactiontimeservice.controller.SatisfactionCalculationController;
import com.techolution.microservice.satisfactiontimeservice.service.impl.SatisfactionCalculationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SatisfactionTimeServiceApplicationTests {

	@InjectMocks
	SatisfactionCalculationServiceImpl satisfactionCalculationServiceImpl;
	
	File file;
	@Before
	public void setUp()
	{
		try {
			file = new File(SatisfactionCalculationController.class.getClassLoader().getResource("data.txt").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void getSatisfactionValueTest() {
		
		Assert.assertNotEquals((Object)satisfactionCalculationServiceImpl.getSatisfactionValue(file), 2400000l);
	}

	@Test
	public void getSatisfactionValueSuccessTest() {
		
		Assert.assertEquals((Object)satisfactionCalculationServiceImpl.getSatisfactionValue(file), 2432996l);
	}

}

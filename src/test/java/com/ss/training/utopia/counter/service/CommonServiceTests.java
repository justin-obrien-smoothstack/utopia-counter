package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
public class CommonServiceTests {

	@Mock
	private UserDao userDao;
	@Mock
	private AirportDao airportDao;
	@InjectMocks
	private CommonService commonService;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void userIsTravelerTest() {
		String username = "Username";
		User traveler = new User(null, null, null, null, "TRAVELER"),
				nonTraveler = new User(null, null, null, null, "COUNTER");
		Mockito.when(userDao.findByUsername(username)).thenReturn(traveler, nonTraveler, null);
		assertTrue(commonService.userIsTraveler(username));
		assertFalse(commonService.userIsTraveler(username));
		assertFalse(commonService.userIsTraveler(username));
	}

	@Test
	public void getUserTest() {
		User user = new User();
		user.setPassword("password");
		Mockito.when(userDao.findByUsername(null)).thenReturn(user);
		assertEquals(user, commonService.getUser(null));
		assertNull(user.getPassword());
	}

	@Test
	public void getAllAirportsTest() {
		Airport[] airportArray = new Airport[0];
		List<Airport> airportList = new ArrayList<Airport>();
		Mockito.when(airportDao.findAll()).thenReturn(airportList);
		assertTrue(Arrays.equals(airportArray, commonService.getAllAirports()));
	}

}

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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTests {

	@Mock
	private UserDao userDao;
	@Mock
	private AirportDao airportDao;
	@Mock
	private FlightDao flightDao;
	@Mock
	private BookingDao bookingDao;
	@InjectMocks
	private BookingService bookingService;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		String password = "password";
		User user = new User(null, null, null, password, null);
		Mockito.when(userDao.save(user)).thenReturn(null);
		assertTrue(bookingService.createUser(user));
		assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
		Mockito.when(userDao.save(user)).thenThrow(new RuntimeException());
		assertNull(bookingService.createUser(user));
	}

	@Test
	public void usernameAvailableTest() {
		String username = "Username";
		Mockito.when(userDao.findByUsername(username)).thenReturn(null, new User());
		assertTrue(bookingService.usernameAvailable(username));
		assertFalse(bookingService.usernameAvailable(username));
		Mockito.when(userDao.findByUsername(username)).thenThrow(new RuntimeException());
		assertNull(bookingService.usernameAvailable(username));
	}

	@Test
	public void userIsTravelerTest() {
		String username = "Username";
		User traveler = new User(null, null, null, null, "TRAVELER"),
				nonTraveler = new User(null, null, null, null, "COUNTER");
		Mockito.when(userDao.findByUsername(username)).thenReturn(traveler, nonTraveler, null);
		assertTrue(bookingService.userIsTraveler(username));
		assertFalse(bookingService.userIsTraveler(username));
		assertFalse(bookingService.userIsTraveler(username));
		Mockito.when(userDao.findByUsername(username)).thenThrow(new RuntimeException());
		assertNull(bookingService.userIsTraveler(username));
	}

	@Test
	public void getAllAirportsTest() {
		Airport[] airportArray = { new Airport() };
		List<Airport> airportList = new ArrayList<Airport>();
		airportList.add(new Airport());
		Mockito.when(airportDao.findAll()).thenReturn(airportList);
		assertTrue(Arrays.equals(airportArray, bookingService.getAllAirports()));
		Mockito.when(airportDao.findAll()).thenThrow(new RuntimeException());
		assertNull(bookingService.getAllAirports());
	}

	@Test
	public void getBookableFlightsTest() {
		Flight[] flightArray = { new Flight() };
		List<Flight> flightList = new ArrayList<Flight>();
		flightList.add(new Flight());
		Mockito.when(flightDao.findBookable(null, null, null)).thenReturn(flightList);
		assertTrue(Arrays.equals(flightArray, bookingService.getBookableFlights(null, null, null)));
		Mockito.when(flightDao.findBookable(null, null, null)).thenThrow(new RuntimeException());
		assertNull(bookingService.getBookableFlights(null, null, null));
	}

	@Test
	public void bookFlightTest() {
		Booking booking = new Booking();
		Flight flight = new Flight(null, null, null, null, (short) 1, null);
		Mockito.when(flightDao.findByFlightId(null)).thenReturn(flight);
		assertTrue(bookingService.bookFlight(booking));
		assertEquals((short) 0, flight.getSeatsAvailable());
		assertFalse(bookingService.bookFlight(booking));
		flight.setSeatsAvailable((short) 10);
		Mockito.when(flightDao.findByFlightId(null)).thenThrow(new RuntimeException());
		assertNull(bookingService.bookFlight(booking));
		Mockito.reset(flightDao);
		Mockito.when(flightDao.findByFlightId(null)).thenReturn(flight);
		Mockito.when(bookingDao.save(booking)).thenThrow(new RuntimeException());
		assertNull(bookingService.bookFlight(booking));
		Mockito.reset(bookingDao);
		Mockito.when(flightDao.save(flight)).thenThrow(new RuntimeException());
		assertNull(bookingService.bookFlight(booking));
	}

}
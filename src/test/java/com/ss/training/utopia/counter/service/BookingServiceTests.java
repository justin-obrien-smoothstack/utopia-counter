package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
		Mockito.when(userDao.save(user)).thenReturn(user);
		assertEquals(user, bookingService.createUser(user));
		assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
	}

	@Test
	public void usernameAvailableTest() {
		String username = "Username";
		Mockito.when(userDao.findByUsername(username)).thenReturn(null, new User());
		assertTrue(bookingService.usernameAvailable(username));
		assertFalse(bookingService.usernameAvailable(username));
	}

	@Test
	public void getBookableFlightsTest() {
		Flight[] flightArray = new Flight[0];
		List<Flight> flightList = new ArrayList<Flight>();
		Mockito.when(flightDao.findBookable(null, null, null)).thenReturn(flightList);
		assertTrue(Arrays.equals(flightArray, bookingService.getBookableFlights(null, null, null)));
	}

	@Test
	public void bookFlightTest() {
		Booking booking = new Booking();
		Flight flight = new Flight(null, null, null, null, (short) 1, null);
		Mockito.when(flightDao.findByFlightId(null)).thenReturn(flight);
		assertTrue(bookingService.bookFlight(booking));
		assertEquals((short) 0, flight.getSeatsAvailable());
		assertFalse(bookingService.bookFlight(booking));
	}

}

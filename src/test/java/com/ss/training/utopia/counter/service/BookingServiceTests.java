package com.ss.training.utopia.counter.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.ChargeCreateParams;

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
	private BookingService service;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		String password = "password";
		User user = new User(null, null, null, password, null);
		when(userDao.save(user)).thenReturn(user);
		assertEquals(user, service.createUser(user));
		assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
	}

	@Test
	public void usernameAvailableTest() {
		String username = "Username";
		when(userDao.findByUsername(username)).thenReturn(null, new User());
		assertTrue(service.usernameAvailable(username));
		assertFalse(service.usernameAvailable(username));
	}

	@Test
	public void getBookableFlightsTest() {
		Flight[] flightArray = new Flight[0];
		List<Flight> flightList = new ArrayList<Flight>();
		when(flightDao.findBookable(null, null, null)).thenReturn(flightList);
		assertTrue(Arrays.equals(flightArray, service.getBookableFlights(null, null, null)));
	}

	@Test
	public void bookFlightTest() throws StripeException {
		final Long HOUR = 3_600_000l;
		Long flightId = 6l, now = Instant.now().toEpochMilli();
		String tokenId = "tok_visa";// , chargeId = "ChargeID";
		Timestamp future = new Timestamp(now + HOUR);
		Booking booking = new Booking(4l, flightId, 3l, true, tokenId);
		Flight flight = new Flight(2l, 8l, future, flightId, (short) 1, 150f);
		when(flightDao.findByFlightId(flightId)).thenReturn(flight);
		assertTrue(service.bookFlight(booking));
		assertNotEquals(tokenId, booking.getStripeId());
		assertEquals((short) 0, flight.getSeatsAvailable());
		assertFalse(service.bookFlight(booking));
		flight.setSeatsAvailable((short) 2);
		when(bookingDao.save(booking)).thenThrow(new RuntimeException());
		booking.setStripeId(tokenId);
		assertThrows(RuntimeException.class, () -> service.bookFlight(booking));
		reset(bookingDao);
		when(flightDao.save(flight)).thenThrow(new RuntimeException());
		booking.setStripeId(tokenId);
		assertThrows(RuntimeException.class, () -> service.bookFlight(booking));
	}

}

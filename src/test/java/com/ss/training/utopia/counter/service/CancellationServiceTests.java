package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.BookingPk;
import com.ss.training.utopia.counter.entity.Flight;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

/**
 * @author Justin O'Brien
 */
@RunWith(MockitoJUnitRunner.class)
public class CancellationServiceTests {

	@Mock
	private FlightDao flightDao;
	@Mock
	private BookingDao bookingDao;
	@InjectMocks
	private CancellationService service;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getCancellablyBookedFlightsTest() {
		List<Flight> flightList = new ArrayList<Flight>();
		Flight[] flightArray = flightList.toArray(new Flight[flightList.size()]);
		Mockito.when(flightDao.findCancellablyBooked(null)).thenReturn(flightList);
		assertTrue(Arrays.equals(flightArray, service.getCancellablyBookedFlights(null)));
	}

	@Test
	public void cancelBookingTest() throws StripeException {
		Stripe.apiKey = "sk_test_51GwErbJwa8c7tq3Odc3WXzypPn0OPpPAd6O5gjvRBBEb15K77CX8D2XSGyyXYgbpNJ0tW52TNRY8ox0o8iKgTkqj00v7B6meHs";
		final Long HOUR = 3_600_000l;
		Short initialSeatsAvailable = 0;
		Long travelerId = 6l, flightId = 4l, now = Instant.now().toEpochMilli();
		Float price = 150f;
		String chargeId = Charge.create(ChargeCreateParams.builder().setAmount((long) (100 * price)).setCurrency("usd")
				.setSource("tok_visa").build()).getId();
		Timestamp future = new Timestamp(now + HOUR);
		Flight flight = new Flight(2l, 3l, future, flightId, initialSeatsAvailable, price);
		Booking booking = new Booking(travelerId, flightId, 8l, true, chargeId);
		Mockito.when(flightDao.findByFlightId(flightId)).thenReturn(flight);
		Mockito.when(bookingDao.findById(new BookingPk(travelerId, flightId))).thenReturn(Optional.of(booking));
		assertTrue(service.cancelBooking(travelerId, flightId));
		assertEquals((short) (initialSeatsAvailable + 1), flight.getSeatsAvailable());
		assertFalse(booking.isActive());
		assertFalse(service.cancelBooking(travelerId, flightId));
	}

}

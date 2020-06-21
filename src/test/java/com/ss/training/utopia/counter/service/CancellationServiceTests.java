package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	private CancellationService cancellationService;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getCancellablyBookedFlightsTest() {
		List<Flight> flightList = new ArrayList<Flight>();
		Flight[] flightArray = flightList.toArray(new Flight[flightList.size()]);
		Mockito.when(flightDao.findCancellablyBooked(null)).thenReturn(flightList);
		assertTrue(Arrays.equals(flightArray, cancellationService.getCancellablyBookedFlights(null)));
	}

	@Test
	public void cancelBookingTest() {
		Short initialSeatsAvailable = 0;
		Flight flight = new Flight(null, null, null, null, initialSeatsAvailable, null);
		Booking booking = new Booking(null, null, null, true, null);
		Mockito.when(flightDao.findByFlightId(null)).thenReturn(flight);
		Mockito.when(bookingDao.findById(new BookingPk(null, null))).thenReturn(Optional.of(booking));
		assertTrue(cancellationService.cancelBooking(null, null));
		assertEquals((short) (initialSeatsAvailable + 1), flight.getSeatsAvailable());
		assertFalse(booking.isActive());
		assertFalse(cancellationService.cancelBooking(null, null));
	}

}

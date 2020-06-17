package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.entity.Flight;

/**
 * @author Justin O'Brien
 */
@RunWith(MockitoJUnitRunner.class)
public class CancellationServiceTests {

	@Mock
	private FlightDao flightDao;
	@InjectMocks
	private CancellationService cancellationService;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getCancellablyBookedFlightsTest() {
		List<Flight> flights = new ArrayList<Flight>();
		Mockito.when(flightDao.findCancellablyBooked(null)).thenReturn(flights);
		assertEquals(flights, cancellationService.getCancellablyBookedFlights(null));
		Mockito.when(flightDao.findCancellablyBooked(null)).thenThrow(new RuntimeException());
		assertNull(cancellationService.getCancellablyBookedFlights(null));
	}

}

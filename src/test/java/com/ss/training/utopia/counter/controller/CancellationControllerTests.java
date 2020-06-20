package com.ss.training.utopia.counter.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.service.CancellationService;

/**
 * @author Justin O'Brien
 */
@WebMvcTest(CancellationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CancellationControllerTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CancellationService service;

	@Test
	public void getCancellablyBookedFlightsTest() throws Exception {
		final Long HOUR = 3_600_000l;
		Long travelerId = 6l, now = Instant.now().toEpochMilli();
		Timestamp future = new Timestamp(now + HOUR), futureTwo = new Timestamp(now + 2 * HOUR);
		Flight[] flights = { new Flight(4l, 6l, future, 2l, (short) 3, 150f),
				new Flight(6l, 4l, futureTwo, 3l, (short) 2, 151f) }, noFlights = {};
		String uri = "/counter/flights/cancellable/traveler/" + travelerId, expectedContent = mapper.writeValueAsString(flights);
		when(service.getCancellablyBookedFlights(travelerId)).thenReturn(flights, noFlights);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
		when(service.getCancellablyBookedFlights(travelerId)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void cancelBookingTest() throws Exception {
		Long travelerId = 4l, flightId = 6l;
		String uri = "/counter/bookings/traveler/" + travelerId + "/flight/" + flightId;
		when(service.cancelBooking(travelerId, flightId)).thenReturn(true, false);
		mvc.perform(put(uri)).andExpect(status().isNoContent()).andExpect(content().string(""));
		mvc.perform(put(uri)).andExpect(status().isBadRequest()).andExpect(content().string(""));
		when(service.cancelBooking(travelerId, flightId)).thenThrow(new RuntimeException());
		mvc.perform(put(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

}

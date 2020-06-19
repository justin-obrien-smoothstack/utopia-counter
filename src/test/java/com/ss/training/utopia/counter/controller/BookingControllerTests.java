package com.ss.training.utopia.counter.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.User;
import com.ss.training.utopia.counter.service.BookingService;

/**
 * @author Justin O'Brien
 */
@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private BookingService service;

	@Test
	public void CreateUserTest() throws Exception {
		User newUser = new User(null, "username", "Name", "Password", "TRAVELER"),
				createdUser = new User(6l, "username", "Name", "HashedPassword", "TRAVELER");
		String uri = "/counter/user", body = mapper.writeValueAsString(newUser),
				expectedContent = mapper.writeValueAsString(createdUser);
		when(service.createUser(newUser)).thenReturn(createdUser);
		mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated())
				.andExpect(content().string(expectedContent));
		when(service.createUser(newUser)).thenThrow(new RuntimeException());
		mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void usernameAvailableTest() throws Exception {
		String username = "Username", uri = "/counter/user/" + username;
		when(service.usernameAvailable(username)).thenReturn(true, false);
		mvc.perform(get(uri)).andExpect(status().isNotFound()).andExpect(content().string(""));
		mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string(""));
		when(service.usernameAvailable(username)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void getBookableFlightsTest() throws Exception {
		final Long HOUR = 3_600_000l;
		Long departId = 4l, arriveId = 2l, travelerId = 6l, now = Instant.now().toEpochMilli();
		Timestamp futureOne = new Timestamp(now + HOUR), futureTwo = new Timestamp(now + 2 * HOUR);
		Flight[] flights = { new Flight(departId, arriveId, futureOne, 3l, (short) 8, 150f),
				new Flight(departId, arriveId, futureTwo, 7l, (short) 5, 151f) };
		String uri = "/counter/flights/bookable/departure/" + departId + "/arrival/" + arriveId + "/traveler/"
				+ travelerId, expectedContent = mapper.writeValueAsString(flights);
		when(service.getBookableFlights(departId, arriveId, travelerId)).thenReturn(flights, new Flight[0]);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
		when(service.getBookableFlights(departId, arriveId, travelerId)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

}

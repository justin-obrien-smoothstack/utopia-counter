package com.ss.training.utopia.counter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.entity.User;
import com.ss.training.utopia.counter.service.CommonService;

/**
 * @author Justin O'Brien
 */
@WebMvcTest(CommonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommonControllerTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CommonService service;

	@Test
	public void userIsTravelerTest() throws Exception {
		String username = "Username", uri = "/counter/traveler/" + username;
		Mockito.when(service.userIsTraveler(username)).thenReturn(true, false);
		mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string(""));
		mvc.perform(get(uri)).andExpect(status().isNotFound()).andExpect(content().string(""));
		Mockito.when(service.userIsTraveler(username)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void getUserTest() throws Exception {
		String username = "Username", uri = "/counter/users/" + username, expectedContent;
		User user = new User(6l, username, "Name", "HashedPassword", "TRAVELER");
		expectedContent = mapper.writeValueAsString(user);
		Mockito.when(service.getUser(username)).thenReturn(user, (User) null);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		mvc.perform(get(uri)).andExpect(status().isNotFound()).andExpect(content().string(""));
		Mockito.when(service.getUser(username)).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

	@Test
	public void getAllAirportsTest() throws Exception {
		Airport[] airports = { new Airport(4l, "City 1"), new Airport(6l, "City 2") }, noAirports = {};
		String uri = "/counter/airports", expectedContent = mapper.writeValueAsString(airports);
		Mockito.when(service.getAllAirports()).thenReturn(airports, noAirports);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string("[]"));
		Mockito.when(service.getAllAirports()).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError()).andExpect(content().string(""));
	}

}

package com.ss.training.utopia.counter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.service.CommonService;

/**
 * @author Justin O'Brien
 */
@WebMvcTest(CommonController.class)
public class CommonControllerTests {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private CommonService service;

	@Test
	public void getAllAirportsTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Airport[] airports = { new Airport(4l, "City 1"), new Airport(6l, "City 2") }, noAirports = {};
		String uri = "/counter/airports", expectedContent = mapper.writeValueAsString(airports),
				expectedEmpty = mapper.writeValueAsString(noAirports);
		Mockito.when(service.getAllAirports()).thenReturn(airports, noAirports);
		mvc.perform(get(uri)).andExpect(status().isOk()).andExpect(content().string(expectedContent));
		mvc.perform(get(uri)).andExpect(status().isNoContent()).andExpect(content().string(expectedEmpty));
		Mockito.when(service.getAllAirports()).thenThrow(new RuntimeException());
		mvc.perform(get(uri)).andExpect(status().isInternalServerError());
	}

}

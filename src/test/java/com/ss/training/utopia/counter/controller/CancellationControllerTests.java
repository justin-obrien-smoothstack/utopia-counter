package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.training.utopia.counter.service.CancellationService;

/**
 * @author Justin O'Brien
 */
@WebMvcTest(CancellationController.class)
public class CancellationControllerTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private CancellationService service;
	
}

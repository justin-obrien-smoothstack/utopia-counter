package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.utopia.counter.service.BookingService;

/**
 * @author Justin O'Brien
 */
@RestController
@RequestMapping(path="/counter")
public class BookingController {

	@Autowired
	BookingService service;
	
}

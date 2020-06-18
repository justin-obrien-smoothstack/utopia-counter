package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.service.CommonService;

/**
 * @author Justin O'Brien
 */
@RestController
@RequestMapping(path = "/counter")
public class CommonController {

	@Autowired
	CommonService service;

	@GetMapping(path = "/airports")
	public ResponseEntity<Airport[]> getAllAirports() {
		Airport[] airports = null;
		HttpStatus status = HttpStatus.OK;
		try {
			airports = service.getAllAirports();
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if(airports != null && airports.length == 0)
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Airport[]>(airports, status);
	}

}

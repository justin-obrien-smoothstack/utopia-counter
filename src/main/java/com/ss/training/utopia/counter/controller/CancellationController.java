package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.service.CancellationService;

/**
 * @author Justin O'Brien
 */
@RestController
@RequestMapping("/counter")
public class CancellationController {

	@Autowired
	CancellationService service;

	@GetMapping(path = "/flights/cancellable/traveler/{travelerId}")
	public ResponseEntity<Flight[]> getCancellablyBookedFlights(@PathVariable Long travelerId) {
		Flight[] flights = null;
		HttpStatus status = HttpStatus.OK;
		try {
			flights = service.getCancellablyBookedFlights(travelerId);
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (flights != null && flights.length == 0)
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flights, status);
	}

	@PutMapping(path = "/bookings/traveler/{travelerId}/flight/{flightId}")
	public ResponseEntity<Object> cancelBooking(@PathVariable Long travelerId, @PathVariable long flightId) {
		Boolean goodRequest = null;
		HttpStatus status = HttpStatus.NO_CONTENT;
		try {
			goodRequest = service.cancelBooking(travelerId, flightId);
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (goodRequest != null && !goodRequest)
			status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<Object>(null, status);
	}

}

package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.User;
import com.ss.training.utopia.counter.service.BookingService;

/**
 * @author Justin O'Brien
 */
@RestController
@RequestMapping("/counter")
public class BookingController {

	@Autowired
	BookingService service;

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		HttpStatus status = HttpStatus.CREATED;
		try {
			user = service.createUser(user);
		} catch (Throwable t) {
			user = null;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<User>(user, status);
	}

	@RequestMapping(method = RequestMethod.HEAD, path = "/user/{username}")
	public ResponseEntity<Object> usernameAvailable(@PathVariable String username) {
		Boolean available = null;
		HttpStatus status = HttpStatus.NOT_FOUND;
		try {
			available = service.usernameAvailable(username);
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (available != null && !available)
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Object>(null, status);
	}

	@GetMapping("/flights/bookable/departure/{departId}/arrival/{arriveId}/traveler/{travelerId}")
	public ResponseEntity<Flight[]> getBookableFlights(@PathVariable Long departId, @PathVariable Long arriveId,
			@PathVariable Long travelerId) {
		Flight[] flights = null;
		HttpStatus status = HttpStatus.OK;
		try {
			flights = service.getBookableFlights(departId, arriveId, travelerId);
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (flights != null && flights.length == 0)
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Flight[]>(flights, status);
	}

	@PostMapping("/booking")
	public ResponseEntity<Object> bookFlight(@RequestBody Booking booking) {
		Boolean goodRequest = null;
		HttpStatus status = HttpStatus.CREATED;
		try {
			goodRequest = service.bookFlight(booking);
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (goodRequest != null && !goodRequest)
			status = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<Object>(null, status);
	}

}

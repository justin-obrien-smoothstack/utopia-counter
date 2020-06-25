package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.entity.User;
import com.ss.training.utopia.counter.service.CommonService;

/**
 * @author Justin O'Brien
 */
@RestController
@RequestMapping("/counter")
public class CommonController {

	@Autowired
	CommonService service;

	@GetMapping(path = "/traveler/{username}")
	public ResponseEntity<Object> userIsTraveler(@PathVariable String username) {
		Boolean isTraveler = null;
		HttpStatus status = HttpStatus.NO_CONTENT;
		try {
			isTraveler = service.userIsTraveler(username);
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (isTraveler != null && !isTraveler)
			status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Object>(null, status);
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		Boolean thrown = false;
		HttpStatus status = HttpStatus.OK;
		User user = null;
		try {
			user = service.getUser(username);
		} catch (Throwable t) {
			thrown = true;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (!thrown && user == null)
			status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<User>(user, status);
	}

	@GetMapping("/airports")
	public ResponseEntity<Airport[]> getAllAirports() {
		HttpStatus status = HttpStatus.OK;
		Airport[] airports = null;
		try {
			airports = service.getAllAirports();
		} catch (Throwable t) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		if (airports != null && airports.length == 0)
			status = HttpStatus.NO_CONTENT;
		return new ResponseEntity<Airport[]>(airports, status);
	}

}

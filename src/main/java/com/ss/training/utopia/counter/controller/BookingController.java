package com.ss.training.utopia.counter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.training.utopia.counter.entity.User;
import com.ss.training.utopia.counter.service.BookingService;

/**
 * @author Justin O'Brien
 */
@RestController
@RequestMapping(path = "/counter")
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

	@GetMapping("/user/{username}")
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

}

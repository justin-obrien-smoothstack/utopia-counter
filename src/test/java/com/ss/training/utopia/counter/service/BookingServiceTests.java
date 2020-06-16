package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTests {

	@Mock
	private UserDao userDao;
	@InjectMocks
	private BookingService bookingService;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserTest() {
		String password = "password";
		User user = new User(null, null, null, password, null);
		Mockito.when(userDao.save(user)).thenReturn(null);
		assertTrue(bookingService.createUser(user));
		assertTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()));
		Mockito.when(userDao.save(user)).thenThrow();
		assertNull(bookingService.createUser(user));
	}

	@Test
	public void usernameAvailableTest() {
		String username = "Username";
		Mockito.when(userDao.findByUsername(username)).thenReturn(null);
		assertTrue(bookingService.usernameAvailable(username));
		Mockito.when(userDao.findByUsername(username)).thenReturn(new User());
		assertFalse(bookingService.usernameAvailable(username));
		Mockito.when(userDao.findByUsername(username)).thenThrow();
		assertNull(bookingService.usernameAvailable(username));
	}

	@Test
	public void userIsTravelerTest() {
		String username = "Username";
		User traveler = new User(null, null, null, null, "TRAVELER"),
				nonTraveler = new User(null, null, null, null, "COUNTER");
		Mockito.when(userDao.findByUsername(username)).thenReturn(traveler);
		assertTrue(bookingService.userIsTraveler(username));
		Mockito.when(userDao.findByUsername(username)).thenReturn(nonTraveler);
		assertFalse(bookingService.userIsTraveler(username));
		Mockito.when(userDao.findByUsername(username)).thenThrow();
		assertNull(bookingService.userIsTraveler(username));
	}

}

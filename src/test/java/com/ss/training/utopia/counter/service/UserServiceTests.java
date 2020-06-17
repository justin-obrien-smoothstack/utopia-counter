package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
public class UserServiceTests {

	@Mock
	private UserDao userDao;
	@InjectMocks
	private UserService userService;

	@BeforeEach
	private void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void userIsTravelerTest() {
		String username = "Username";
		User traveler = new User(null, null, null, null, "TRAVELER"),
				nonTraveler = new User(null, null, null, null, "COUNTER");
		Mockito.when(userDao.findByUsername(username)).thenReturn(traveler, nonTraveler, null);
		assertTrue(userService.userIsTraveler(username));
		assertFalse(userService.userIsTraveler(username));
		assertFalse(userService.userIsTraveler(username));
		Mockito.when(userDao.findByUsername(username)).thenThrow(new RuntimeException());
		assertNull(userService.userIsTraveler(username));
	}

	@Test
	public void getUserTest() {
		User user = new User();
		Mockito.when(userDao.findByUsername(null)).thenReturn(user);
		assertEquals(user, userService.getUser(null));
		Mockito.when(userDao.findByUsername(null)).thenThrow(new RuntimeException());
		assertNull(userService.getUser(null));
	}

}

package com.ss.training.utopia.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		Long userId = 1l;
		String password = "password";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		User newUser = new User(null, null, null, password, null), savedUser = new User(userId, null, null, null, null);
		Mockito.when(userDao.save(newUser)).thenReturn(savedUser);
		assertEquals(userId, bookingService.createUser(newUser));
		assertTrue(encoder.matches(password, newUser.getPassword()));
	}

}
package com.ss.training.utopia.test.counter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.User;
import com.ss.training.utopia.counter.service.BookingService;

/**
 * @author Justin O'Brien
 */
@SpringBootTest
public class BookingServiceTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private BookingService bookingService;

	@Test
	public void createUserTest() {
		Long mockUserId = (long) 1;
		User mockNewUser = new User(), mockSavedUser = new User();
		Mockito.when(mockSavedUser.getUserId()).thenReturn(mockUserId);
		Mockito.when(userDao.save(mockNewUser)).thenReturn(mockSavedUser);
		assertEquals(mockUserId, bookingService.createUser(mockNewUser));
	}

}

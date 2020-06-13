package com.ss.training.utopia.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
@Component
public class BookingService {

	@Autowired
	AirportDao airportDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FlightDao flightDao;
	@Autowired
	BookingDao bookingDao;

	public Long createUser(User user) {
		try {
			return userDao.save(user).getUserId();
		} catch (Throwable t) {
			return null;
		}
	}

	public Boolean isUserTraveler(Long userId) {
		User user;
		try {
			user = userDao.findById(userId).orElseGet(null);
		} catch (Throwable t) {
			return null;
		}
		return ("TRAVELER".equals(user.getRole()));
	}

}

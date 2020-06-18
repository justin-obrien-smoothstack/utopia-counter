package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
public class CommonService {

	@Autowired
	UserDao userDao;
	@Autowired
	AirportDao airportDao;

	public Boolean userIsTraveler(String username) {
		User user = userDao.findByUsername(username);
		return (user != null && "TRAVELER".equals(user.getRole()));
	}

	public User getUser(String username) {
		return userDao.findByUsername(username);
	}
	
	public Airport[] getAllAirports() {
		List<Airport> airports = airportDao.findAll();
		return airports.toArray(new Airport[airports.size()]);
	}

}

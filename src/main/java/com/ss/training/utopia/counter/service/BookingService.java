package com.ss.training.utopia.counter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.UserDao;

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

}

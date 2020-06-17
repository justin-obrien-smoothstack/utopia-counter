package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.entity.Flight;

/**
 * @author Justin O'Brien
 */
public class CancellationService {

	@Autowired
	FlightDao flightDao;

	public List<Flight> getCancellablyBookedFlights(Long userId) {
		try {
			return flightDao.findCancellablyBooked(userId);
		} catch (Throwable t) {
			return null;
		}
	}

}

package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.Airport;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.Flight;
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

	public Airport[] getAllAirports() {
		List<Airport> airports;
		try {
			airports = airportDao.findAll();
		} catch (Throwable t) {
			return null;
		}
		return airports.toArray(new Airport[airports.size()]);
	}

	public Flight[] getBookableFlights(Integer departId, Integer arriveId, Integer travelerId) {
		List<Flight> flights;
		try {
			flights = flightDao.findBookable(departId, arriveId, travelerId);
		} catch (Throwable t) {
			return null;
		}
		return flights.toArray(new Flight[flights.size()]);
	}

	@Transactional
	public Boolean bookFlight(Booking booking) {
		Flight flight;
		try {
			flight = flightDao.findByFlightId(booking.getFlightId());
		} catch (Throwable t) {
			return null;
		}
		if (flight.getSeatsAvailable() <= 0)
			return false;
		flight.setSeatsAvailable((short) (flight.getSeatsAvailable() - 1));
		// take payment & set stripeId
		try {
			bookingDao.save(booking);
			flightDao.save(flight);
		} catch (Throwable t) {
			// cancel payment
			return null;
		}
		return true;
	}

}

package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.counter.dao.AirportDao;
import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.User;

/**
 * @author Justin O'Brien
 */
@Service
public class BookingService {

	@Autowired
	AirportDao airportDao;
	@Autowired
	UserDao userDao;
	@Autowired
	FlightDao flightDao;
	@Autowired
	BookingDao bookingDao;

	public User createUser(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userDao.save(user);
	}

	public Boolean usernameAvailable(String username) {
		return userDao.findByUsername(username) == null;
	}

	public Flight[] getBookableFlights(Long departId, Long arriveId, Long travelerId) {
		List<Flight> flights = flightDao.findBookable(departId, arriveId, travelerId);
		return flights.toArray(new Flight[flights.size()]);
	}

	@Transactional
	public Boolean bookFlight(Booking booking) {
		Flight flight = flightDao.findByFlightId(booking.getFlightId());
		if (flight.getSeatsAvailable() <= 0)
			return false;
		// take payment & set stripeId
		flight.setSeatsAvailable((short) (flight.getSeatsAvailable() - 1));
		bookingDao.save(booking);
		flightDao.save(flight);
		// if a DAO throws when trying to save, have controller call a function to do a
		// refund
		// or maybe have controller call a separate method to do transaction in the
		// first place
		return true;
	}

}

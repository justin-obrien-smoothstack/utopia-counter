package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.UserDao;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.RefundCreateParams;

/**
 * @author Justin O'Brien
 */
@Service
public class BookingService {

	@Autowired
	UserDao userDao;
	@Autowired
	FlightDao flightDao;
	@Autowired
	BookingDao bookingDao;
	@Autowired
	StripeWrapper stripe;

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
	public Boolean bookFlight(Booking booking) throws StripeException {
		Flight flight = flightDao.findByFlightId(booking.getFlightId());
		if (flight.getSeatsAvailable() <= 0)
			return false;
		flight.setSeatsAvailable((short) (flight.getSeatsAvailable() - 1));
		booking.setStripeId(
				stripe.createChargeGetId(ChargeCreateParams.builder().setAmount((long) (100 * flight.getPrice()))
						.setCurrency("usd").setSource(booking.getStripeId()).build()));
		try {
			bookingDao.save(booking);
			flightDao.save(flight);
		} catch (Throwable t) {
			stripe.createRefund(RefundCreateParams.builder().setCharge(booking.getStripeId()).build());
			throw t;
		}
		return true;
	}

}

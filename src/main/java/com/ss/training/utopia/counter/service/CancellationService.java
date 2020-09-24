package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.counter.Secrets;
import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.dao.StripeDao;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.BookingPk;
import com.ss.training.utopia.counter.entity.Flight;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;

/**
 * @author Justin O'Brien
 */
@Service
public class CancellationService {

	@Autowired
	FlightDao flightDao;
	@Autowired
	BookingDao bookingDao;
	@Autowired
	StripeDao stripeDao;

	public Flight[] getCancellablyBookedFlights(Long travelerId) {
		List<Flight> flights = flightDao.findCancellablyBooked(travelerId);
		return flights.toArray(new Flight[flights.size()]);
	}

	@Transactional
	public Boolean cancelBooking(Long travelerId, Long flightId) throws StripeException {
		Flight flight;
		Booking booking;
		Stripe.apiKey = Secrets.stripeKey;
		flight = flightDao.findByFlightId(flightId);
		booking = bookingDao.findById(new BookingPk(travelerId, flightId)).get();
		if (!booking.isActive())
			return false;
		flight.setSeatsAvailable((short) (flight.getSeatsAvailable() + 1));
		booking.setActive(false);
		flightDao.save(flight);
		bookingDao.save(booking);
		stripeDao.refund(booking.getStripeId());
		return true;
	}

}

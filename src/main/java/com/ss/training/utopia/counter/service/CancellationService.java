package com.ss.training.utopia.counter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ss.training.utopia.counter.dao.BookingDao;
import com.ss.training.utopia.counter.dao.FlightDao;
import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.BookingPk;
import com.ss.training.utopia.counter.entity.Flight;

/**
 * @author Justin O'Brien
 */
public class CancellationService {

	@Autowired
	FlightDao flightDao;
	@Autowired
	BookingDao bookingDao;

	public List<Flight> getCancellablyBookedFlights(Long travelerId) {
		try {
			return flightDao.findCancellablyBooked(travelerId);
		} catch (Throwable t) {
			return null;
		}
	}

	@Transactional
	public Boolean cancelBooking(Long travelerId, Long flightId) {
		Flight flight;
		Booking booking;
		flight = flightDao.findByFlightId(flightId);
		booking = bookingDao.findById(new BookingPk(travelerId, flightId)).get();
		if (!booking.isActive())
			return false;
		flight.setSeatsAvailable((short) (flight.getSeatsAvailable() + 1));
		booking.setActive(false);
		flightDao.save(flight);
		bookingDao.save(booking);
		// issue Stripe refund
		return true;
	}

}

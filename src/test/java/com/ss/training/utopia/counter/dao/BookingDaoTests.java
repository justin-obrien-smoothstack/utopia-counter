package com.ss.training.utopia.counter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.Flight;

/**
 * @author Justin O'Brien
 */
@DataJpaTest
public class BookingDaoTests {

	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private BookingDao bookingDao;

	@Test
	public void findCancellableTest() {
		final Long HOUR = 3_600_000l;
		Long thisTravelerId = 1l, otherTravelerId = 2l, futureFlightId = 1l, pastFlightId = 2l,
				otherFutureFlightId = 3l, now = Instant.now().toEpochMilli();
		Timestamp past = new Timestamp(now - HOUR), future = new Timestamp(now + HOUR);
		Flight futureFlight = new Flight(1l, 2l, future, futureFlightId, null, null),
				pastFlight = new Flight(1l, 2l, past, pastFlightId, null, null),
				otherFutureFlight = new Flight(2l, 1l, future, otherFutureFlightId, null, null);
		Booking cancellableBooking = new Booking(thisTravelerId, futureFlightId, null, true, null),
				otherTravelerBooking = new Booking(otherTravelerId, futureFlightId, null, true, null),
				pastFlightBooking = new Booking(thisTravelerId, pastFlightId, null, true, null),
				inactiveBooking = new Booking(thisTravelerId, otherFutureFlightId, null, false, null);
		List<Booking> foundBookings;
		testEntityManager.persist(futureFlight);
		testEntityManager.persist(pastFlight);
		testEntityManager.persist(otherFutureFlight);
		testEntityManager.persist(cancellableBooking);
		testEntityManager.persist(otherTravelerBooking);
		testEntityManager.persist(pastFlightBooking);
		testEntityManager.persist(inactiveBooking);
		testEntityManager.flush();
		foundBookings = bookingDao.findCancellable(thisTravelerId);
		assertEquals(foundBookings.size(), 1);
		assertEquals(foundBookings.get(0), cancellableBooking);
	}

}

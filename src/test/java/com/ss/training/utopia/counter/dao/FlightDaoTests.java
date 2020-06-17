package com.ss.training.utopia.counter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
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
public class FlightDaoTests {

	@Autowired
	private TestEntityManager testEntityManager;
	@Autowired
	private FlightDao flightDao;

	@AfterEach
	public void after() {
		testEntityManager.clear();
	}

	@Test
	public void findBookableTest() {
		final Long HOUR = 3_600_000l;
		Long thisDepartId = 1l, thisArriveId = 1l, otherDepartId = 2l, otherArriveId = 2l, thisTravelerId = 1l,
				otherTravelerId = 2l, unbookedFlightId = 1l, inactiveBookedFlightId = unbookedFlightId + 1,
				activeBookedFlightId = inactiveBookedFlightId + 1, now = Instant.now().toEpochMilli();
		Timestamp past = new Timestamp(now - HOUR), future = new Timestamp(now + HOUR),
				futureTwo = new Timestamp(now + 2 * HOUR), futureThree = new Timestamp(now + 3 * HOUR),
				futureFour = new Timestamp(now + 4 * HOUR);
		Flight unbookedFlight = new Flight(thisDepartId, thisArriveId, future, unbookedFlightId, (short) 1, null),
				inactiveBookedFlight = new Flight(thisDepartId, thisArriveId, futureTwo, inactiveBookedFlightId,
						(short) 1, null),
				wrongDepartFlight = new Flight(otherDepartId, thisArriveId, future, activeBookedFlightId + 1, (short) 1,
						null),
				wrongArriveFlight = new Flight(thisDepartId, otherArriveId, future, activeBookedFlightId + 2, (short) 1,
						null),
				pastFlight = new Flight(thisDepartId, thisArriveId, past, activeBookedFlightId + 3, (short) 1, null),
				fullFlight = new Flight(thisDepartId, thisArriveId, futureThree, activeBookedFlightId + 4, (short) 0,
						null),
				activeBookedFlight = new Flight(thisDepartId, thisArriveId, futureFour, activeBookedFlightId, (short) 1,
						null);
		Booking inactiveBooking = new Booking(thisTravelerId, inactiveBookedFlightId, null, false, null),
				otherTravelerBookingOne = new Booking(otherTravelerId, unbookedFlightId, null, true, null),
				otherTravelerBookingTwo = new Booking(otherTravelerId, inactiveBookedFlightId, null, true, null),
				activeBooking = new Booking(thisTravelerId, activeBookedFlightId, null, true, null);
		Set<Flight> expectedFlights = new HashSet<Flight>(),
				actualFlights = new HashSet<Flight>(flightDao.findBookable(thisDepartId, thisArriveId, thisTravelerId));
		assertEquals(expectedFlights, actualFlights);
		testEntityManager.persist(unbookedFlight);
		testEntityManager.persist(inactiveBookedFlight);
		testEntityManager.persist(wrongDepartFlight);
		testEntityManager.persist(wrongArriveFlight);
		testEntityManager.persist(pastFlight);
		testEntityManager.persist(fullFlight);
		testEntityManager.persist(activeBookedFlight);
		testEntityManager.persist(inactiveBooking);
		testEntityManager.persist(otherTravelerBookingOne);
		testEntityManager.persist(otherTravelerBookingTwo);
		testEntityManager.persist(activeBooking);
		testEntityManager.flush();
		expectedFlights.add(unbookedFlight);
		expectedFlights.add(inactiveBookedFlight);
		actualFlights = new HashSet<Flight>(flightDao.findBookable(thisDepartId, thisArriveId, thisTravelerId));
		assertEquals(expectedFlights, actualFlights);
	}

	@Test
	public void findByFlightIdTest() {
		Long thisFlightId = 1l, otherFlightId = 2l, notAFlightId = 3l;
		Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());
		Flight thisFlight = new Flight(1l, 2l, timestamp, thisFlightId, (short) 0, null),
				otherFlight = new Flight(2l, 1l, timestamp, otherFlightId, (short) 0, null);
		testEntityManager.persist(thisFlight);
		testEntityManager.persist(otherFlight);
		assertEquals(thisFlight, flightDao.findByFlightId(thisFlightId));
		assertNull(flightDao.findByFlightId(notAFlightId));
	}

	@Test
	public void findCancellablyBookedTest() {
		final Long HOUR = 3_600_000l;
		Long thisTravelerId = 1l, otherTravelerId = 2l, futureFlightId = 1l, pastFlightId = 2l,
				otherFutureFlightId = 3l, now = Instant.now().toEpochMilli();
		Timestamp past = new Timestamp(now - HOUR), future = new Timestamp(now + HOUR);
		Flight cancellablyBookedFlight = new Flight(1l, 2l, future, futureFlightId, null, null),
				pastFlight = new Flight(1l, 2l, past, pastFlightId, null, null),
				unbookedByThisTravelerFlight = new Flight(2l, 1l, future, otherFutureFlightId, null, null);
		Booking cancellableBooking = new Booking(thisTravelerId, futureFlightId, null, true, null),
				otherTravelerBooking = new Booking(otherTravelerId, futureFlightId, null, true, null),
				pastFlightBooking = new Booking(thisTravelerId, pastFlightId, null, true, null),
				inactiveBooking = new Booking(thisTravelerId, otherFutureFlightId, null, false, null);
		List<Flight> expectedFlights = new ArrayList<Flight>(), actualFlights = flightDao.findCancellablyBooked(thisTravelerId);
		assertEquals(expectedFlights, actualFlights);
		testEntityManager.persist(cancellablyBookedFlight);
		testEntityManager.persist(pastFlight);
		testEntityManager.persist(unbookedByThisTravelerFlight);
		testEntityManager.persist(cancellableBooking);
		testEntityManager.persist(otherTravelerBooking);
		testEntityManager.persist(pastFlightBooking);
		testEntityManager.persist(inactiveBooking);
		testEntityManager.flush();
		expectedFlights.add(cancellablyBookedFlight);
		actualFlights = flightDao.findCancellablyBooked(thisTravelerId);
		assertEquals(expectedFlights, actualFlights);
	}

	@Test
	public void findCancellablyBookedTest() {
		final Long HOUR = 3_600_000l;
		Long thisTravelerId = 1l, otherTravelerId = 2l, futureFlightId = 1l, pastFlightId = 2l,
				otherFutureFlightId = 3l, now = Instant.now().toEpochMilli();
		Timestamp past = new Timestamp(now - HOUR), future = new Timestamp(now + HOUR);
		Flight cancellablyBookedFlight = new Flight(1l, 2l, future, futureFlightId, null, null),
				pastFlight = new Flight(1l, 2l, past, pastFlightId, null, null),
				unbookedByThisTravelerFlight = new Flight(2l, 1l, future, otherFutureFlightId, null, null);
		Booking cancellableBooking = new Booking(thisTravelerId, futureFlightId, null, true, null),
				otherTravelerBooking = new Booking(otherTravelerId, futureFlightId, null, true, null),
				pastFlightBooking = new Booking(thisTravelerId, pastFlightId, null, true, null),
				inactiveBooking = new Booking(thisTravelerId, otherFutureFlightId, null, false, null);
		List<Flight> expectedFlights = new ArrayList<Flight>(), foundFlights;
		expectedFlights.add(cancellablyBookedFlight);
		testEntityManager.persist(cancellablyBookedFlight);
		testEntityManager.persist(pastFlight);
		testEntityManager.persist(unbookedByThisTravelerFlight);
		testEntityManager.persist(cancellableBooking);
		testEntityManager.persist(otherTravelerBooking);
		testEntityManager.persist(pastFlightBooking);
		testEntityManager.persist(inactiveBooking);
		testEntityManager.flush();
		foundFlights = flightDao.findCancellablyBooked(thisTravelerId);
		assertEquals(expectedFlights, foundFlights);
	}

}

package com.ss.training.utopia.counter.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

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
		final Long HOUR = (long) 3_600_000;
		Long thisDepartId = (long) 1, thisArriveId = (long) 1, otherDepartId = (long) 2, otherArriveId = (long) 2,
				thisTravelerId = (long) 1, otherTravelerId = (long) 2, unbookedFlightId = (long) 1,
				inactiveBookedFlightId = unbookedFlightId + 1, activeBookedFlightId = inactiveBookedFlightId + 1,
				now = Instant.now().toEpochMilli();
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
		Set<Flight> expectedBookableFlights = new HashSet<Flight>(), actualBookableFlights;
		expectedBookableFlights.add(unbookedFlight);
		expectedBookableFlights.add(inactiveBookedFlight);
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
		actualBookableFlights = new HashSet<Flight>(flightDao.findBookable(thisDepartId, thisArriveId, thisTravelerId));
		assertEquals(expectedBookableFlights, actualBookableFlights);
	}

	@Test
	public void findByFlightIdTest() {
		Long thisFlightId = (long) 1, otherFlightId = (long) 2;
		Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());
		Flight thisFlight = new Flight((long) 1, (long) 2, timestamp, thisFlightId, (short) 0, null),
				otherFlight = new Flight((long) 2, (long) 1, timestamp, otherFlightId, (short) 0, null);
		testEntityManager.persist(thisFlight);
		testEntityManager.persist(otherFlight);
		assertEquals(thisFlight, flightDao.findByFlightId(thisFlightId));
	}

}

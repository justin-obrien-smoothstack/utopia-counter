package com.ss.training.utopia.counter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.FlightPk;

/**
 * @author Justin O'Brien
 */
@Repository
public interface FlightDao extends JpaRepository<Flight, FlightPk> {

	@Query("SELECT f FROM Flight f WHERE f.departId = ?1 AND f.arriveId = ?2 "
			+ "AND f.departTime > CURRENT_TIMESTAMP AND f.seatsAvailable > 0 "
			+ "AND f.flightId NOT IN (SELECT b.flightId FROM Booking b WHERE b.travelerId = ?3 AND b.active = true)")
	public List<Flight> findBookable(Long departId, Long arriveId, Long travelerId);

	public Flight findByFlightId(Long flightId);

	@Query("SELECT f FROM Flight f WHERE f.departTime > CURRENT_TIMESTAMP AND f.flightId IN "
			+ "(SELECT b.flightId FROM Booking b WHERE b.travelerId = ?1 AND b.active = true)")
	public List<Flight> findCancellablyBooked(Long travelerId);

}

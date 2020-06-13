package com.ss.training.utopia.counter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.counter.entity.Flight;
import com.ss.training.utopia.counter.entity.FlightPk;

/**
 * @author Justin O'Brien
 */
@Repository
public interface FlightDao extends JpaRepository<Flight, FlightPk> {

	public List<Flight> findByDepartIdAndArriveId(Long departId, Long arriveId);

	public Flight findByFlightId(Long flightId);

}

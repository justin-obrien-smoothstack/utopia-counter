package com.ss.training.utopia.counter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.BookingPk;

/**
 * @author Justin O'Brien
 */
@Repository
public interface BookingDao extends JpaRepository<Booking, BookingPk> {

	@Query("SELECT b FROM Booking b WHERE b.travelerId = ?1 AND b.active = true AND b.flightId NOT IN "
			+ "(SELECT f.flightId FROM Flight f WHERE f.departTime <= CURRENT_TIMESTAMP)")
	public List<Booking> findCancellable(Long travelerId);

}

package com.ss.training.utopia.counter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.BookingPk;

/**
 * @author Justin O'Brien
 */
public interface BookingDao extends JpaRepository<Booking, BookingPk> {

	@Query("SELECT b FROM tbl_booking b JOIN tbl_flight f WHERE b.travelerId = ?1 AND b.active = true AND f.departTime > CURRENT_TIMESTAMP")
	public List<Booking> findCancellable(Integer travelerId);

}

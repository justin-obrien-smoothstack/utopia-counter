package com.ss.training.utopia.counter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.counter.entity.Booking;
import com.ss.training.utopia.counter.entity.BookingPk;

/**
 * @author Justin O'Brien
 */
@Repository
public interface BookingDao extends JpaRepository<Booking, BookingPk> {
}

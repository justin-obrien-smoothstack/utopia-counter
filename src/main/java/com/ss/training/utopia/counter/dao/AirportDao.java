package com.ss.training.utopia.counter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.training.utopia.counter.entity.Airport;

/**
 * @author Justin O'Brien
 */
@Repository
public interface AirportDao extends JpaRepository<Airport, Long> {

}

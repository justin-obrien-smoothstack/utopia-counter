package com.ss.training.utopia.counter.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Justin O'Brien
 */
@Entity
@Table(name = "tbl_flight")
@IdClass(FlightPk.class)
public class Flight implements Serializable {

	private static final long serialVersionUID = -9104220295315031366L;

	@Id
	@ManyToOne
	@JoinColumn(name="departId", referencedColumnName = "airportId")
	private Airport departAirport;
	
	@Id
	@ManyToOne
	@JoinColumn(name="arriveId", referencedColumnName = "airportId")
	private Airport arriveAirport;
	
	@Id
	@Column
	private Timestamp departTime;

	@Column(unique = true)
	private Long flightId;

	@Column
	private Short seatsAvailable;

	@Column
	private Float price;

	/**
	 * 
	 */
	public Flight() {
	}

	/**
	 * @param departAirport
	 * @param arriveAirport
	 * @param departTime
	 * @param flightId
	 * @param seatsAvailable
	 * @param price
	 */
	public Flight(Airport departAirport, Airport arriveAirport, Timestamp departTime, Long flightId, Short seatsAvailable,
			Float price) {
		this.departAirport = departAirport;
		this.arriveAirport = arriveAirport;
		this.departTime = departTime;
		this.flightId = flightId;
		this.seatsAvailable = seatsAvailable;
		this.price = price;
	}

	/**
	 * @return the seatsAvailable
	 */
	public Short getSeatsAvailable() {
		return seatsAvailable;
	}

	/**
	 * @param seatsAvailable the seatsAvailable to set
	 */
	public void setSeatsAvailable(Short seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}

	/**
	 * @return the departId
	 */
	public Airport getDepartAirport() {
		return departAirport;
	}

	/**
	 * @return the arriveId
	 */
	public Airport getArriveAirport() {
		return arriveAirport;
	}

	/**
	 * @return the departTime
	 */
	public Timestamp getDepartTime() {
		return departTime;
	}

	/**
	 * @return the flightId
	 */
	public Long getFlightId() {
		return flightId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arriveAirport == null) ? 0 : arriveAirport.hashCode());
		result = prime * result + ((departAirport == null) ? 0 : departAirport.hashCode());
		result = prime * result + ((departTime == null) ? 0 : departTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (arriveAirport == null) {
			if (other.arriveAirport != null)
				return false;
		} else if (!arriveAirport.equals(other.arriveAirport))
			return false;
		if (departAirport == null) {
			if (other.departAirport != null)
				return false;
		} else if (!departAirport.equals(other.departAirport))
			return false;
		if (departTime == null) {
			if (other.departTime != null)
				return false;
		} else if (!departTime.equals(other.departTime))
			return false;
		return true;
	}

}

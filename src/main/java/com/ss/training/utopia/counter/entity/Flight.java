package com.ss.training.utopia.counter.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author Justin O'Brien
 */
@Table(name = "tbl_flight")
@IdClass(FlightPk.class)
public class Flight implements Serializable {

	private static final long serialVersionUID = -8418526716923540697L;

	@Id
	@Column
	private int departId, arriveId;

	@Id
	@Column
	private LocalDateTime departTime;

	@Column
	private int flightId, seatsAvailable;

	@Column
	private double price;

	/**
	 * @return the seatsAvailable
	 */
	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	/**
	 * @param seatsAvailable the seatsAvailable to set
	 */
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the departId
	 */
	public int getDepartId() {
		return departId;
	}

	/**
	 * @return the arriveId
	 */
	public int getArriveId() {
		return arriveId;
	}

	/**
	 * @return the departTime
	 */
	public LocalDateTime getDepartTime() {
		return departTime;
	}

	/**
	 * @return the flightId
	 */
	public int getFlightId() {
		return flightId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arriveId;
		result = prime * result + departId;
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
		if (arriveId != other.arriveId)
			return false;
		if (departId != other.departId)
			return false;
		if (departTime == null) {
			if (other.departTime != null)
				return false;
		} else if (!departTime.equals(other.departTime))
			return false;
		return true;
	}

}

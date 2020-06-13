package com.ss.training.utopia.counter.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
	@Column
	private Long departId, arriveId;

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
	public Long getDepartId() {
		return departId;
	}

	/**
	 * @return the arriveId
	 */
	public Long getArriveId() {
		return arriveId;
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
		result = prime * result + ((arriveId == null) ? 0 : arriveId.hashCode());
		result = prime * result + ((departId == null) ? 0 : departId.hashCode());
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
		if (arriveId == null) {
			if (other.arriveId != null)
				return false;
		} else if (!arriveId.equals(other.arriveId))
			return false;
		if (departId == null) {
			if (other.departId != null)
				return false;
		} else if (!departId.equals(other.departId))
			return false;
		if (departTime == null) {
			if (other.departTime != null)
				return false;
		} else if (!departTime.equals(other.departTime))
			return false;
		return true;
	}

}

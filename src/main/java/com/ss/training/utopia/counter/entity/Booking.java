package com.ss.training.utopia.counter.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author Justin O'Brien
 */
@Entity
@Table(name = "tbl_booking")
@IdClass(BookingPk.class)
public class Booking implements Serializable {

	private static final long serialVersionUID = -3688288514632867852L;

	@Id
	@Column
	private Long travelerId, flightId;

	@Column
	private Long bookerId;

	@Column
	private Boolean active;

	@Column
	private String stripeId;

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the travelerId
	 */
	public Long getTravelerId() {
		return travelerId;
	}

	/**
	 * @return the flightId
	 */
	public Long getFlightId() {
		return flightId;
	}

	/**
	 * @return the bookerId
	 */
	public Long getBookerId() {
		return bookerId;
	}

	/**
	 * @return the stripeId
	 */
	public String getStripeId() {
		return stripeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightId == null) ? 0 : flightId.hashCode());
		result = prime * result + ((travelerId == null) ? 0 : travelerId.hashCode());
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
		Booking other = (Booking) obj;
		if (flightId == null) {
			if (other.flightId != null)
				return false;
		} else if (!flightId.equals(other.flightId))
			return false;
		if (travelerId == null) {
			if (other.travelerId != null)
				return false;
		} else if (!travelerId.equals(other.travelerId))
			return false;
		return true;
	}

}

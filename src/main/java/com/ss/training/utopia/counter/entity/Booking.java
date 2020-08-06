package com.ss.training.utopia.counter.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Justin O'Brien
 */
@Entity
@Table(name = "tbl_booking")
@IdClass(BookingPk.class)
public class Booking implements Serializable {

	private static final long serialVersionUID = -5964334491109305574L;

	@Id
	@Column
	private Long travelerId, flightId;

	@ManyToOne
	@JoinColumn(name = "travelerId", referencedColumnName = "userId", insertable = false, updatable = false)
	@JsonManagedReference
	private User traveler;

	@ManyToOne
	@JoinColumn(name = "flightId", referencedColumnName = "flightId", insertable = false, updatable = false)
	@JsonManagedReference
	private Flight flight;

	@ManyToOne
	@JoinColumn(name = "bookerId", referencedColumnName = "userId", insertable = false, updatable = false)
	@JsonManagedReference
	private User booker;

	@Column
	private Long bookerId;

	@Column
	private Boolean active;

	@Column
	private String stripeId;

	/**
	 * 
	 */
	public Booking() {
	}

	/**
	 * @param travelerId
	 * @param flightId
	 * @param bookerId
	 * @param active
	 * @param stripeId
	 */
	public Booking(Long travelerId, Long flightId, Long bookerId, Boolean active, String stripeId) {
		this.travelerId = travelerId;
		this.flightId = flightId;
		this.bookerId = bookerId;
		this.active = active;
		this.stripeId = stripeId;
	}

	/**
	 * @return the traveler
	 */
	public User getTraveler() {
		return traveler;
	}

	/**
	 * @return the flight
	 */
	public Flight getFlight() {
		return flight;
	}

	/**
	 * @return the booker
	 */
	public User getBooker() {
		return booker;
	}

	/**
	 * @return the active
	 */
	public Boolean isActive() {
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

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
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

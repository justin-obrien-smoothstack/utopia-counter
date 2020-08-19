package com.ss.training.utopia.counter.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Justin O'Brien
 */
public class FlightPk implements Serializable {

	private static final long serialVersionUID = 7884891349820641524L;

	private Airport departAirport, arriveAirport;
	private Timestamp departTime;

	/**
	 * 
	 */
	public FlightPk() {
	}

	/**
	 * @param departAirport
	 * @param arriveAirport
	 * @param departTime
	 */
	public FlightPk(Airport departAirport, Airport arriveAirport, Timestamp departTime) {
		this.departAirport = departAirport;
		this.arriveAirport = arriveAirport;
		this.departTime = departTime;
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
		FlightPk other = (FlightPk) obj;
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

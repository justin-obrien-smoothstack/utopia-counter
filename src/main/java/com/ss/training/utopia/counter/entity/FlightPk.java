package com.ss.training.utopia.counter.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Justin O'Brien
 */
public class FlightPk implements Serializable {

	private static final long serialVersionUID = 7884891349820641524L;

	private Long departId, arriveId;
	private Timestamp departTime;

	/**
	 * 
	 */
	public FlightPk() {
	}

	/**
	 * @param departId
	 * @param arriveId
	 * @param departTime
	 */
	public FlightPk(Long departId, Long arriveId, Timestamp departTime) {
		this.departId = departId;
		this.arriveId = arriveId;
		this.departTime = departTime;
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
		FlightPk other = (FlightPk) obj;
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

package com.ss.training.utopia.counter.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Justin O'Brien
 */
public class FlightPk implements Serializable {

	private static final long serialVersionUID = 5767548512201809275L;
	
	private int departId, arriveId;
	private LocalDateTime departTime;

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
		FlightPk other = (FlightPk) obj;
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

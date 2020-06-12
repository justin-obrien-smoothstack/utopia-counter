package com.ss.training.utopia.counter.entity;

import java.io.Serializable;

/**
 * @author Justin O'Brien
 */
public class FlightPk implements Serializable {

	private static final long serialVersionUID = -2255441595895564963L;
	private int departId, arriveId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arriveId;
		result = prime * result + departId;
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
		return true;
	}

}

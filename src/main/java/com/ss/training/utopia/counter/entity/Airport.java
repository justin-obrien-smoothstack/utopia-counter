package com.ss.training.utopia.counter.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Justin O'Brien
 */
@Entity
@Table(name = "tbl_airport")
public class Airport implements Serializable {

	private static final long serialVersionUID = 5424938813745219349L;

	@Id
	@Column
	private Long airportId;

	@Column
	private String name;

	/**
	 * 
	 */
	public Airport() {
	}

	/**
	 * @param airportId
	 * @param name
	 */
	public Airport(Long airportId, String name) {
		this.airportId = airportId;
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the airportId
	 */
	public Long getAirportId() {
		return airportId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airportId == null) ? 0 : airportId.hashCode());
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
		Airport other = (Airport) obj;
		if (airportId == null) {
			if (other.airportId != null)
				return false;
		} else if (!airportId.equals(other.airportId))
			return false;
		return true;
	}

}

package com.meli.osm.location.entities

import com.google.gson.annotations.SerializedName;

/**
 * This class represents a simplification of an specific point.
 * 
 * @author fabianbertetto
 *
 */
class PointLocation {
	/**
	 * Country where a point is located.
	 */
	private Country country;
	/**
	 * State where a point is located.
	 */
	private State state;
	/**
	 * City where a point is located.
	 */
	private City city;
	/**
	 * Zip Code of the place where a point is located.
	 */
	@SerializedName("zip_code")
	private String zipCode;
	/**
	 * Street Name where a point is located.
	 */
	@SerializedName("street_name")
	private String streetName;
	/**
	 * Street Number where a point is located.
	 */
	@SerializedName("street_number")
	private String streetNumber;

	/**
	 * Gets Country.
	 * @return country State where a point is located.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets Country.
	 * @param country name.
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * Gets State.
	 * @return State where a point is located. 
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets State.
	 * @param state name.
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Gets City.
	 * @return City where a point is located.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets City.
	 * @param city name.
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Gets Zip Code.
	 * @return zip code of the place where a point is located.
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets Zip Code.
	 * @param zipCode of the place where a point is located.
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets Street Name.
	 * @return Street Name where a point is located.
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * Sets Street Name.
	 * @param streetName
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**
	 * Gets Street Number.
	 * @return Street Number where a point is located.
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * Sets Street Number.
	 * @param street Number
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
}

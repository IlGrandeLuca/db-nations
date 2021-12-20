package org.generation.italy;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Country {

	private int id;
	private String name;
	private BigDecimal area;
	private LocalDate nationalDay;
	private String countryCode2;
	private String countryCode3;
	private int regionId;

	/**
	 * @param id
	 * @param name
	 * @param area
	 * @param nationalDay
	 * @param countryCode2
	 * @param countryCode3
	 * @param regionId
	 */
	public Country(int id, String name, BigDecimal area, LocalDate nationalDay, String countryCode2, String countryCode3,
			int regionId) {
		this.id = id;
		this.name = name;
		this.area = area;
		this.nationalDay = nationalDay;
		this.countryCode2 = countryCode2;
		this.countryCode3 = countryCode3;
		this.regionId = regionId;
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
	 * @return the area
	 */
	public BigDecimal getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(BigDecimal area) {
		this.area = area;
	}

	/**
	 * @return the nationalDay
	 */
	public LocalDate getNationalDay() {
		return nationalDay;
	}

	/**
	 * @param nationalDay the nationalDay to set
	 */
	public void setNationalDay(LocalDate nationalDay) {
		this.nationalDay = nationalDay;
	}

	/**
	 * @return the countryCode2
	 */
	public String getCountryCode2() {
		return countryCode2;
	}

	/**
	 * @param countryCode2 the countryCode2 to set
	 */
	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}

	/**
	 * @return the countryCode3
	 */
	public String getCountryCode3() {
		return countryCode3;
	}

	/**
	 * @param countryCode3 the countryCode3 to set
	 */
	public void setCountryCode3(String countryCode3) {
		this.countryCode3 = countryCode3;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the regionId
	 */
	public int getRegionId() {
		return regionId;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", " + (name != null ? "name=" + name + ", " : "") + "area=" + area + ", "
				+ (nationalDay != null ? "nationalDay=" + nationalDay + ", " : "")
				+ (countryCode2 != null ? "countryCode2=" + countryCode2 + ", " : "")
				+ (countryCode3 != null ? "countryCode3=" + countryCode3 + ", " : "") + "regionId=" + regionId + "]";
	}

}

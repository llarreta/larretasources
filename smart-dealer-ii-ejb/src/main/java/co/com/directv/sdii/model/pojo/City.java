package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * City entity. @author MyEclipse Persistence Tools
 */

public class City implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7429483691201982208L;
	private Long id;
	private State state;
	private String cityName;
	private String cityCode;

	// Constructors

	/** default constructor */
	public City() {
	}

	/**
	 * 
	 * @param state
	 * @param cityName
	 * @param cityCode
	 */
	public City(State state, String cityName, String cityCode) {
		this.state = state;
		this.cityName = cityName;
		this.cityCode = cityCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Override
	public String toString() {
		return "City [cityCode=" + cityCode + ", id=" + id + "]";
	}

}
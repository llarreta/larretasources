package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * State entity. @author MyEclipse Persistence Tools
 */

public class State implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3590566873305309031L;
	private Long id;
	private Country country;
	private String stateName;
	private String stateCode;

	// Constructors

	/** default constructor */
	public State() {
	}

	/** minimal constructor */
	public State(Country country, String stateName, String stateCode) {
		this.country = country;
		this.stateName = stateName;
		this.stateCode = stateCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		return "State [id=" + id + ", stateCode=" + stateCode + "]";
	}
	
}
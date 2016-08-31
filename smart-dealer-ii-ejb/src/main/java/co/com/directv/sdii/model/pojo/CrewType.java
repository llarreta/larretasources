package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

// default package


/**
 * CrewType entity. @author MyEclipse Persistence Tools
 */

public class CrewType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006203175203253388L;
	private Long id;
	private String crewTypeName;
	private String crewTypeCode;

	// Constructors

	/** default constructor */
	public CrewType() {
	}

	/** minimal constructor */
	public CrewType(String crewTypeName, String crewTypeCode) {
		this.crewTypeName = crewTypeName;
		this.crewTypeCode = crewTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrewTypeName() {
		return this.crewTypeName;
	}

	public void setCrewTypeName(String crewTypeName) {
		this.crewTypeName = crewTypeName;
	}

	public String getCrewTypeCode() {
		return this.crewTypeCode;
	}

	public void setCrewTypeCode(String crewTypeCode) {
		this.crewTypeCode = crewTypeCode;
	}

	@Override
	public String toString() {
		return "CrewType [crewTypeCode=" + crewTypeCode + ", id=" + id + "]";
	}

}
package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * InconsistencyType entity. @author MyEclipse Persistence Tools
 */

public class InconsistencyType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7812250392556135578L;
	private Long id;
	private String incTypeCode;
	private String incTypeName;
	private String incTypeDescription;
	private String isActive;

	// Constructors

	/** default constructor */
	public InconsistencyType() {
	}

	/** minimal constructor */
	public InconsistencyType(String incTypeCode, String incTypeName,
			String isActive) {
		this.incTypeCode = incTypeCode;
		this.incTypeName = incTypeName;
		this.isActive = isActive;
	}

	/** full constructor */
	public InconsistencyType(String incTypeCode, String incTypeName,
			String incTypeDescription, String isActive) {
		this.incTypeCode = incTypeCode;
		this.incTypeName = incTypeName;
		this.incTypeDescription = incTypeDescription;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIncTypeCode() {
		return this.incTypeCode;
	}

	public void setIncTypeCode(String incTypeCode) {
		this.incTypeCode = incTypeCode;
	}

	public String getIncTypeName() {
		return this.incTypeName;
	}

	public void setIncTypeName(String incTypeName) {
		this.incTypeName = incTypeName;
	}

	public String getIncTypeDescription() {
		return this.incTypeDescription;
	}

	public void setIncTypeDescription(String incTypeDescription) {
		this.incTypeDescription = incTypeDescription;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "InconsistencyType [id=" + id + ", incTypeCode=" + incTypeCode
				+ "]";
	}
	
}
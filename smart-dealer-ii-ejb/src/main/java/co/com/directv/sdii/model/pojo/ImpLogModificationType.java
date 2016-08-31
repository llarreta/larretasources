package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * ImpLogModificationType entity. @author MyEclipse Persistence Tools
 */

public class ImpLogModificationType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 195809965099689375L;
	private Long id;
	private String modTypeCode;
	private String modTypeName;
	private String isActive;

	// Constructors

	/** default constructor */
	public ImpLogModificationType() {
	}

	/** minimal constructor */
	public ImpLogModificationType(String modTypeCode, String modTypeName,
			String isActive) {
		this.modTypeCode = modTypeCode;
		this.modTypeName = modTypeName;
		this.isActive = isActive;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModTypeCode() {
		return this.modTypeCode;
	}

	public void setModTypeCode(String modTypeCode) {
		this.modTypeCode = modTypeCode;
	}

	public String getModTypeName() {
		return this.modTypeName;
	}

	public void setModTypeName(String modTypeName) {
		this.modTypeName = modTypeName;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ImpLogModificationType [id=" + id + ", modTypeCode="
				+ modTypeCode + "]";
	}

}
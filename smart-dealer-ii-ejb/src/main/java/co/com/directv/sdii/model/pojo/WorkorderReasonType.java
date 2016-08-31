package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * WorkorderReasonType entity. @author MyEclipse Persistence Tools
 */

public class WorkorderReasonType implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7559381022179266817L;
	private Long id;
	private String woReasonTypeName;
	private String woReasonTypeCode;

	// Constructors

	/** default constructor */
	public WorkorderReasonType() {
	}

	/** minimal constructor */
	public WorkorderReasonType(String woReasonTypeName, String woReasonTypeCode) {
		this.woReasonTypeName = woReasonTypeName;
		this.woReasonTypeCode = woReasonTypeCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWoReasonTypeName() {
		return this.woReasonTypeName;
	}

	public void setWoReasonTypeName(String woReasonTypeName) {
		this.woReasonTypeName = woReasonTypeName;
	}

	public String getWoReasonTypeCode() {
		return this.woReasonTypeCode;
	}

	public void setWoReasonTypeCode(String woReasonTypeCode) {
		this.woReasonTypeCode = woReasonTypeCode;
	}

	@Override
	public String toString() {
		return "WorkorderReasonType [id=" + id + ", woReasonTypeCode="
				+ woReasonTypeCode + "]";
	}
	
}
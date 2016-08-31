package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * WorkorderReasonCategory entity. @author MyEclipse Persistence Tools
 */

public class WorkorderReasonCategory implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1734036231250152277L;
	private Long id;
	private WorkorderReasonType workorderReasonType;
	private String woReasonCategoryName;
	private String woReasonCategoryCode;

	// Constructors

	/** default constructor */
	public WorkorderReasonCategory() {
	}

	/** minimal constructor */
	public WorkorderReasonCategory(WorkorderReasonType workorderReasonType,
			String woReasonCategoryName, String woReasonCategoryCode) {
		this.workorderReasonType = workorderReasonType;
		this.woReasonCategoryName = woReasonCategoryName;
		this.woReasonCategoryCode = woReasonCategoryCode;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkorderReasonType getWorkorderReasonType() {
		return this.workorderReasonType;
	}

	public void setWorkorderReasonType(WorkorderReasonType workorderReasonType) {
		this.workorderReasonType = workorderReasonType;
	}

	public String getWoReasonCategoryName() {
		return this.woReasonCategoryName;
	}

	public void setWoReasonCategoryName(String woReasonCategoryName) {
		this.woReasonCategoryName = woReasonCategoryName;
	}

	public String getWoReasonCategoryCode() {
		return this.woReasonCategoryCode;
	}

	public void setWoReasonCategoryCode(String woReasonCategoryCode) {
		this.woReasonCategoryCode = woReasonCategoryCode;
	}

	@Override
	public String toString() {
		return "WorkorderReasonCategory [id=" + id + ", woReasonCategoryCode="
				+ woReasonCategoryCode + "]";
	}
}
package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * SpearRuleStatus entity. @author MyEclipse Persistence Tools
 */

public class SpearRuleStatus implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -238302097505651957L;
	private Long id;
	private WorkorderStatus workorderStatusByFromStatus;
	private WorkorderStatus workorderStatusByToStatus;

	// Constructors

	/** default constructor */
	public SpearRuleStatus() {
	}

	/** full constructor */
	public SpearRuleStatus(WorkorderStatus workorderStatusByFromStatus,
			WorkorderStatus workorderStatusByToStatus) {
		this.workorderStatusByFromStatus = workorderStatusByFromStatus;
		this.workorderStatusByToStatus = workorderStatusByToStatus;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkorderStatus getWorkorderStatusByFromStatus() {
		return this.workorderStatusByFromStatus;
	}

	public void setWorkorderStatusByFromStatus(
			WorkorderStatus workorderStatusByFromStatus) {
		this.workorderStatusByFromStatus = workorderStatusByFromStatus;
	}

	public WorkorderStatus getWorkorderStatusByToStatus() {
		return this.workorderStatusByToStatus;
	}

	public void setWorkorderStatusByToStatus(
			WorkorderStatus workorderStatusByToStatus) {
		this.workorderStatusByToStatus = workorderStatusByToStatus;
	}

	@Override
	public String toString() {
		return "SpearRuleStatus [id=" + id + ", workorderStatusByFromStatus="
				+ workorderStatusByFromStatus + ", workorderStatusByToStatus="
				+ workorderStatusByToStatus + "]";
	}
}
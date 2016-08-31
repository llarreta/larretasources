package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * WorkOrderElementId entity. @author MyEclipse Persistence Tools
 */

public class WorkOrderElementId implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7457454856369513514L;
	private Long elementTypeId;
	private Long soId;

	// Constructors

	/** default constructor */
	public WorkOrderElementId() {
	}

	/** full constructor */
	public WorkOrderElementId(Long elementTypeId, Long soId) {
		this.elementTypeId = elementTypeId;
		this.soId = soId;
	}

	// Property accessors

	public Long getElementTypeId() {
		return this.elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public Long getSoId() {
		return this.soId;
	}

	public void setSoId(Long soId) {
		this.soId = soId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WorkOrderElementId))
			return false;
		WorkOrderElementId castOther = (WorkOrderElementId) other;

		return ((this.getElementTypeId() == castOther.getElementTypeId()) || (this
				.getElementTypeId() != null
				&& castOther.getElementTypeId() != null && this
				.getElementTypeId().equals(castOther.getElementTypeId())))
				&& ((this.getSoId() == castOther.getSoId()) || (this.getSoId() != null
						&& castOther.getSoId() != null && this.getSoId()
						.equals(castOther.getSoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getElementTypeId() == null ? 0 : this.getElementTypeId()
						.hashCode());
		result = 37 * result
				+ (getSoId() == null ? 0 : this.getSoId().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "WorkOrderElementId [elementTypeId=" + elementTypeId + ", soId="
				+ soId + "]";
	}
	

}
package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * ReferenceModification entity. @author cduarte
 */

public class AdjustmentModification implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 456326954443337476L;
	private Long id;
	private Adjustment adjustment;
	private AdjustmentStatus adjustmentStatus;
	private Date modificationDate;
	private User userModification;

	// Constructors
	public AdjustmentModification() {
		super();
	}

	public AdjustmentModification(Long id, Adjustment adjustment,
			AdjustmentStatus adjustmentStatus, Date modificationDate,
			User userModification) {
		super();
		this.id = id;
		this.adjustment = adjustment;
		this.adjustmentStatus = adjustmentStatus;
		this.modificationDate = modificationDate;
		this.userModification = userModification;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Adjustment getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Adjustment adjustment) {
		this.adjustment = adjustment;
	}

	public AdjustmentStatus getAdjustmentStatus() {
		return adjustmentStatus;
	}

	public void setAdjustmentStatus(AdjustmentStatus adjustmentStatus) {
		this.adjustmentStatus = adjustmentStatus;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public User getUserModification() {
		return userModification;
	}

	public void setUserModification(User userModification) {
		this.userModification = userModification;
	}

	@Override
	public String toString() {
		return "AdjustmentModification [id=" + id + "]";
	}

	
}
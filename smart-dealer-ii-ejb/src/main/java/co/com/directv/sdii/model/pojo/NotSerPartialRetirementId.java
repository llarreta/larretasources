package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * NotSerPartialRetirementId entity. @author MyEclipse Persistence Tools
 */

public class NotSerPartialRetirementId implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4615835453136888039L;
	private Long id;
	private Double retirementQuantity;
	private Date retirementDate;
	private WarehouseElement warehouseElement;

	// Constructors

	/** default constructor */
	public NotSerPartialRetirementId() {
	}

	/** full constructor */
	public NotSerPartialRetirementId(Long id, Double retirementQuantity,
			Date retirementDate, WarehouseElement warehouseElement) {
		this.id = id;
		this.retirementQuantity = retirementQuantity;
		this.retirementDate = retirementDate;
		this.warehouseElement = warehouseElement;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRetirementQuantity() {
		return this.retirementQuantity;
	}

	public void setRetirementQuantity(Double retirementQuantity) {
		this.retirementQuantity = retirementQuantity;
	}

	public Date getRetirementDate() {
		return this.retirementDate;
	}

	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = retirementDate;
	}

	public WarehouseElement getWarehouseElement() {
		return this.warehouseElement;
	}

	public void setWarehouseElement(WarehouseElement warehouseElement) {
		this.warehouseElement = warehouseElement;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NotSerPartialRetirementId))
			return false;
		NotSerPartialRetirementId castOther = (NotSerPartialRetirementId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getRetirementQuantity() == castOther
						.getRetirementQuantity()) || (this
						.getRetirementQuantity() != null
						&& castOther.getRetirementQuantity() != null && this
						.getRetirementQuantity().equals(
								castOther.getRetirementQuantity())))
				&& ((this.getRetirementDate() == castOther.getRetirementDate()) || (this
						.getRetirementDate() != null
						&& castOther.getRetirementDate() != null && this
						.getRetirementDate().equals(
								castOther.getRetirementDate())))
				&& ((this.getWarehouseElement() == castOther
						.getWarehouseElement()) || (this.getWarehouseElement() != null
						&& castOther.getWarehouseElement() != null && this
						.getWarehouseElement().equals(
								castOther.getWarehouseElement())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37
				* result
				+ (getRetirementQuantity() == null ? 0 : this
						.getRetirementQuantity().hashCode());
		result = 37
				* result
				+ (getRetirementDate() == null ? 0 : this.getRetirementDate()
						.hashCode());
		result = 37
				* result
				+ (getWarehouseElement() == null ? 0 : this
						.getWarehouseElement().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "NotSerPartialRetirementId [id=" + id + "]";
	}
}
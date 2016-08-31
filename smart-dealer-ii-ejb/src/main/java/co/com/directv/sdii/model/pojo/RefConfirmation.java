package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * RefConfirmation entity. @author MyEclipse Persistence Tools
 */

public class RefConfirmation implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5306068860362692613L;
	private Long id;
	private ReferenceElementItem referenceElementItem;
	private Double confirmedQuantity;
	private Double pendQuantity;
	private Date comfirmDate;
	private Long userId;

	// Constructors

	/** default constructor */
	public RefConfirmation() {
	}

	/** full constructor */
	public RefConfirmation(ReferenceElementItem referenceElementItem,
			Double confirmedQuantity, Double pendQuantity, Date comfirmDate,
			Long userId) {
		this.referenceElementItem = referenceElementItem;
		this.confirmedQuantity = confirmedQuantity;
		this.pendQuantity = pendQuantity;
		this.comfirmDate = comfirmDate;
		this.userId = userId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReferenceElementItem getReferenceElementItem() {
		return this.referenceElementItem;
	}

	public void setReferenceElementItem(
			ReferenceElementItem referenceElementItem) {
		this.referenceElementItem = referenceElementItem;
	}

	public Double getConfirmedQuantity() {
		return this.confirmedQuantity;
	}

	public void setConfirmedQuantity(Double confirmedQuantity) {
		this.confirmedQuantity = confirmedQuantity;
	}

	public Double getPendQuantity() {
		return this.pendQuantity;
	}

	public void setPendQuantity(Double pendQuantity) {
		this.pendQuantity = pendQuantity;
	}

	public Date getComfirmDate() {
		return this.comfirmDate;
	}

	public void setComfirmDate(Date comfirmDate) {
		this.comfirmDate = comfirmDate;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "RefConfirmation [id=" + id + "]";
	}
}
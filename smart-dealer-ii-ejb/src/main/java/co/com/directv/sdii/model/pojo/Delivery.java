package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;

/**
 * Delivery entity. @author MyEclipse Persistence Tools
 */

public class Delivery implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 12796042001107155L;
	private Long id;
	private ReferenceElementItem referenceElementItem;
	private Reference reference;
	private Date deliveryDate;
	private Double deliveryQuantity;
	private Long userId;

	// Constructors

	/** default constructor */
	public Delivery() {
	}

	/** full constructor */
	public Delivery(ReferenceElementItem referenceElementItem,
			Reference reference, Date deliveryDate, Double deliveryQuantity,
			Long userId) {
		this.referenceElementItem = referenceElementItem;
		this.reference = reference;
		this.deliveryDate = deliveryDate;
		this.deliveryQuantity = deliveryQuantity;
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

	public Reference getReference() {
		return this.reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Double getDeliveryQuantity() {
		return this.deliveryQuantity;
	}

	public void setDeliveryQuantity(Double deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + id + "]";
	}

}
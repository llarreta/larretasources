package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;


/**
 * ReferenceElementItem entity. @author MyEclipse Persistence Tools
 */

public class ReferenceElementItem implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1072437650933211325L;
	private Long id;
	private ItemStatus itemStatus;
	private Element element;
	private Reference reference;
	private Double refQuantity;

	// Constructors

	/** default constructor */
	public ReferenceElementItem() {
	}

	/** minimal constructor */
	public ReferenceElementItem(ItemStatus itemStatus, Element element,
			Reference reference, Double refQuantity) {
		this.itemStatus = itemStatus;
		this.element = element;
		this.reference = reference;
		this.refQuantity = refQuantity;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemStatus getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Reference getReference() {
		return this.reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public Double getRefQuantity() {
		return this.refQuantity;
	}

	public void setRefQuantity(Double refQuantity) {
		this.refQuantity = refQuantity;
	}

	@Override
	public String toString() {
		return "ReferenceElementItem [id=" + id + "]";
	}
	
}
package co.com.directv.sdii.model.pojo;

// default package


/**
 * RefQuantityControlItem entity. @author MyEclipse Persistence Tools
 */

public class RefQuantityControlItem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Long id;
	private ElementType elementType;
	private Reference reference;
	private Double requiredQty;
	private Double includedQty;

	// Constructors

	/** default constructor */
	public RefQuantityControlItem() {
	}

	/** minimal constructor */
	public RefQuantityControlItem(Long id, ElementType elementType,Reference reference,
			Double requiredQty, Double includedQty) {
		this.id = id;
		this.reference = reference;
		this.elementType = elementType;
		this.requiredQty = requiredQty;
		this.includedQty = includedQty;
	}

	
	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Double getRequiredQty() {
		return this.requiredQty;
	}

	public void setRequiredQty(Double requiredQty) {
		this.requiredQty = requiredQty;
	}

	public Double getIncludedQty() {
		return this.includedQty;
	}

	public void setIncludedQty(Double includedQty) {
		this.includedQty = includedQty;
	}	

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	

}
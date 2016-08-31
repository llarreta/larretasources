package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;

/**
 * ShippingOrderElement entity. @author MyEclipse Persistence Tools
 */

public class ShippingOrderElement implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5268041784355646530L;
	
	private Long id;
	private ElementModel elementModel;
	private ElementType elementType;
	private Long soId;
	private Element element;

	// Constructors

	/** default constructor */
	public ShippingOrderElement() {
	}

	/** minimal constructor */
	public ShippingOrderElement(ElementType elementType, Long soId) {
		this.elementType = elementType;
		this.soId = soId;
	}

	/** minimal constructor */
	public ShippingOrderElement(ElementModel elementModel,
			ElementType elementType, Long soId) {
		this.elementModel = elementModel;
		this.elementType = elementType;
		this.soId = soId;
	}
	
	/** minimal constructor */
	public ShippingOrderElement(ElementModel elementModel,
			ElementType elementType, Element element) {
		this.elementModel = elementModel;
		this.elementType = elementType;
		this.element = element;		
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ElementModel getElementModel() {
		return this.elementModel;
	}

	public void setElementModel(ElementModel elementModel) {
		this.elementModel = elementModel;
	}

	public ElementType getElementType() {
		return this.elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public Long getSoId() {
		return this.soId;
	}

	public void setSoId(Long soId) {
		this.soId = soId;
	}
	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	@Override
	public String toString() {
		return "ShippingOrderElement [id=" + id + "]";
	}
}
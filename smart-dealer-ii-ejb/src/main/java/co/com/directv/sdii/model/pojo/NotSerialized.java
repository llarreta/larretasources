package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;



/**
 * NotSerialized entity. @author MyEclipse Persistence Tools
 */

public class NotSerialized implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7037328771433901759L;
	private Long elementId;
	private Element element;
	private Date registrationDate;
	private Double initialQuantity;

	// Constructors

	/** default constructor */
	public NotSerialized() {
	}

	/** minimal constructor */
	public NotSerialized(Element element, Date registrationDate,
			Double initialQuantity) {
		this.element = element;
		this.registrationDate = registrationDate;
		this.initialQuantity = initialQuantity;
	}

	

	// Property accessors

	public Long getElementId() {
		return this.elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Double getInitialQuantity() {
		return this.initialQuantity;
	}

	public void setInitialQuantity(Double initialQuantity) {
		this.initialQuantity = initialQuantity;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elementId == null) ? 0 : elementId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotSerialized other = (NotSerialized) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotSerialized [element=" + element + ", elementId=" + elementId
				+ "]";
	}
	
}
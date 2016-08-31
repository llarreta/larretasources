package co.com.directv.sdii.model.pojo;

import java.util.Date;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;



/**
 * Serialized entity. @author MyEclipse Persistence Tools
 */

public class Serialized implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4842103177506572387L;		
	private Long elementId;
	private Serialized serialized;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Element element;
	private String serialCode;
	private String ird;
	@BusinessRequired
	private Date registrationDate;

	// Constructors

	
	
	/** default constructor */
	public Serialized() {
	}

	public Serialized(Long elementId) {
		super();
		this.elementId = elementId;
	}

	/** minimal constructor */
	public Serialized(Element element) {
		this.element = element;
	}
	
	/** minimal constructor */
	public Serialized(Element element, String serialCode, Date registrationDate) {
		this.element = element;
		this.serialCode = serialCode;
		this.registrationDate = registrationDate;
	}

	/** full constructor */
	public Serialized(Serialized serialized, Element element,
			String serialCode, String ird, Date registrationDate) {
		this.serialized = serialized;
		this.element = element;
		this.serialCode = serialCode;
		this.ird = ird;
		this.registrationDate = registrationDate;
	}
	
	/** full constructor */
	public Serialized(Long elementId,Serialized serialized, Element element,
			String serialCode, String ird, Date registrationDate) {
		this.elementId = elementId;
		this.serialized = serialized;
		this.element = element;
		this.serialCode = serialCode;
		this.ird = ird;
		this.registrationDate = registrationDate;
	}
	
	public Serialized(Long elementId,Long serializedId,String serialCode,String ird,Date registrationDate){
		this.elementId = elementId;
		if( elementId != null ){
			this.element = new Element();
			this.element.setId(elementId);
		}
		if( serializedId != null ){
			this.serialized = new Serialized();
			this.serialized.setElementId(serializedId);
		}
		this.serialCode = serialCode;
		this.ird = ird;
		this.registrationDate = registrationDate;
	}

	// Property accessors
	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	public Serialized getSerialized() {
		return serialized;
	}

	public void setSerialized(Serialized serialized) {
		this.serialized = serialized;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getIrd() {
		return ird;
	}

	public void setIrd(String ird) {
		this.ird = ird;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serialized other = (Serialized) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Serialized [elementId=" + elementId + ", serialCode="
				+ serialCode + "]"+this.registrationDate;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
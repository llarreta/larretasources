package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;



/**
 * RequiredServiceElement entity. @author MyEclipse Persistence Tools
 */

public class RequiredServiceElement  implements java.io.Serializable,Auditable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -5843650706275466190L;
	private RequiredServiceElementId id;
    @BusinessRequired(isComplexType=true, fieldNameRequired="id") 
	private Service service;
    @BusinessRequired(isComplexType=true, fieldNameRequired="id")
    private ElementType elementType;
    private Double quantity;


    // Constructors

    /** default constructor */
    public RequiredServiceElement() {
    }

    
    /** full constructor */
    public RequiredServiceElement(RequiredServiceElementId id, Service service, ElementType elementType, Double quantity) {
        this.id = id;
        this.service = service;
        this.elementType = elementType;
        this.quantity = quantity;
    }

   
    // Property accessors

    public RequiredServiceElementId getId() {
        return this.id;
    }
    
    public void setId(RequiredServiceElementId id) {
        this.id = id;
    }

    public Service getService() {
        return this.service;
    }
    
    public void setService(Service service) {
        this.service = service;
    }

    public Double getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

	public ElementType getElementType() {
		return elementType;
	}


	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}


	@Override
	public String toString() {
		return "RequiredServiceElement [elementType=" + elementType + ", id="
				+ id + "]";
	}
}
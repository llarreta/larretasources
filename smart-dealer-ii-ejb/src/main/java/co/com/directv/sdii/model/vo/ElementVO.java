/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.Element;

/**
 * Objeto que encapsula la información de un Element
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Element    
 */
public class ElementVO extends Element implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6672587208388401133L;
	
	private SerializedVO serializedElement;
	private NotSerializedVO notSerializedElement;
	
	//jvelez 23/06/2011 -- Se adiciona para almacenar la cantidad a mover en los movimientos de elementos no serializados
	private Double movedQuantity;
	
	public SerializedVO getSerializedElement() {
		return serializedElement;
	}


	public void setSerializedElement(SerializedVO serializedElement) {
		this.serializedElement = serializedElement;
	}


	public NotSerializedVO getNotSerializedElement() {
		return notSerializedElement;
	}


	public void setNotSerializedElement(NotSerializedVO notSerializedElement) {
		this.notSerializedElement = notSerializedElement;
	}


	public Double getMovedQuantity() {
		return movedQuantity;
	}


	public void setMovedQuantity(Double movedQuantity) {
		this.movedQuantity = movedQuantity;
	}

	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(getId());
		buff.append("|");
		buff.append(getIsSerialized() == null ? "" : getIsSerialized());
		buff.append("|");
		buff.append(getSerializedElement() == null ? "" : getSerializedElement().getSerialCode());
		buff.append("|");
		buff.append(getNotSerializedElement() == null ? "" : getNotSerializedElement().getElement().getElementType().getTypeElementName());
		buff.append("|");
		buff.append(getLote() == null ? "" : getLote());
		buff.append("|");
		buff.append(getElementType().getElementModel() == null ? "" : getElementType().getElementModel().getModelName());
		buff.append("|");
		buff.append(getElementType() == null ? "" : getElementType().getTypeElementName());
		buff.append("|");
		buff.append(getSupplier() == null ? "" : getSupplier().getSupplierName());
		buff.append("|");
		buff.append(getWarrantyPeriod() == null ? "" : getWarrantyPeriod());
		buff.append("|");
		buff.append(getElementStatus() == null ? "" : getElementStatus().getElementStatusName());
		return buff.toString();
	}
		
}

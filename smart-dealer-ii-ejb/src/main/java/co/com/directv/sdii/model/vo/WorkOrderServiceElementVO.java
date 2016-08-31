/**
 * Creado 16/07/2010 16:17:20
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

/**
 * Objeto que encapsula la información de un elemento que es usado en la atención de
 * un servicio de una work order
 * 
 * Fecha de Creación: 16/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WorkOrderServiceElementVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -683033315449733630L;

	private Long elementTypeId;
	
	private String elementTypeCode;
	
	private String elementSerial;
	
	private Double quantity;
	
	private boolean objectRecovery;
	
	private boolean defectiveObject;
	
	private boolean objectExchange;
	
	private String isSerialized;
	
	private Boolean isIrdElement;
	
	private WorkOrderServiceElementVO linkedWOServiceElement;
	
	private String modelName;

	public Long getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}
	
	public String getElementSerial() {
		return elementSerial;
	}

	public void setElementSerial(String elementSerial) {
		this.elementSerial = elementSerial;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean isObjectRecovery() {
		return objectRecovery;
	}

	public void setObjectRecovery(boolean objectRecovery) {
		this.objectRecovery = objectRecovery;
	}

	public boolean isDefectiveObject() {
		return defectiveObject;
	}

	public void setDefectiveObject(boolean defectiveObject) {
		this.defectiveObject = defectiveObject;
	}

	public WorkOrderServiceElementVO getLinkedWOServiceElement() {
		return linkedWOServiceElement;
	}

	public void setLinkedWOServiceElement(
			WorkOrderServiceElementVO linkedWOServiceElement) {
		this.linkedWOServiceElement = linkedWOServiceElement;
	}

	public String getIsSerialized() {
		return isSerialized;
	}

	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}

	public boolean isObjectExchange() {
		return objectExchange;
	}

	public void setObjectExchange(boolean objectExchange) {
		this.objectExchange = objectExchange;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Boolean getIsIrdElement() {
		return isIrdElement;
	}

	public void setIsIrdElement(Boolean isIrdElement) {
		this.isIrdElement = isIrdElement;
	}	
}

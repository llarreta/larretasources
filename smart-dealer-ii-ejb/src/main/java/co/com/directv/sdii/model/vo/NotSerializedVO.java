/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.NotSerialized;
import co.com.directv.sdii.model.pojo.ReferenceElementItem;

/**
 * Objeto que encapsula la información de un NotSerialized
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.NotSerialized    
 */
public class NotSerializedVO extends NotSerialized implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4775986405635594941L;
	private Double physicalQuantity;
	private Double actualQuantity;
	private Double differenceQuantity;
	private String codeElement;
	// Variable utilizada para almacenar la cantidad de elementos movidos hacia una bodega
	private Double movedQuantity;
	
	public Double getMovedQuantity() {
		return movedQuantity;
	}
	public void setMovedQuantity(Double movedQuantity) {
		this.movedQuantity = movedQuantity;
	}

	/**
	 * Varialbe utilizada para saber la cantidad de elementos a devovler en CU ADS 64
	 */
	private Double quantityToReturn;
	
	private ReferenceElementItem referenceElementItem;
	
	public Double getQuantityToReturn() {
		return quantityToReturn;
	}
	public void setQuantityToReturn(Double quantityToReturn) {
		this.quantityToReturn = quantityToReturn;
	}
	public Double getPhysicalQuantity() {
		return physicalQuantity;
	}
	public void setPhysicalQuantity(Double physicalQuantity) {
		this.physicalQuantity = physicalQuantity;
	}
	public Double getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public Double getDifferenceQuantity() {
		return differenceQuantity;
	}
	public void setDifferenceQuantity(Double differenceQuantity) {
		this.differenceQuantity = differenceQuantity;
	}
	public String getCodeElement() {
		return codeElement;
	}
	public void setCodeElement(String codeElement) {
		this.codeElement = codeElement;
	}
	public ReferenceElementItem getReferenceElementItem() {
		return referenceElementItem;
	}
	public void setReferenceElementItem(ReferenceElementItem referenceElementItem) {
		this.referenceElementItem = referenceElementItem;
	}
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		buff.append(super.getElementId());
		buff.append("|");
		buff.append(super.getElement().getLote() == null ? "" : super.getElement().getLote());
		buff.append("|");
		/*buff.append(super.getElement().getElementBrand() == null ? "" : super.getElement().getElementBrand().getBrandName());
		buff.append("|");*/
		/*buff.append(super.getElement().getElementClass() == null ? "" : super.getElement().getElementClass().getElementClassName());
		buff.append("|");*/
		buff.append(super.getElement().getElementType().getElementModel() == null ? "" : super.getElement().getElementType().getElementModel().getModelName());
		buff.append("|");
		buff.append(super.getElement().getElementType() == null ? "" : super.getElement().getElementType().getTypeElementName());
		buff.append("|");
		buff.append(super.getElement().getElementType().getMeasureUnit() == null ? "" : super.getElement().getElementType().getMeasureUnit().getUnitName());
		buff.append("|");
		buff.append(super.getElement().getSupplier() == null ? "" : super.getElement().getSupplier().getSupplierName());
		return buff.toString();
	}

}

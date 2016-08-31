/**
 * 
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

/**
 * Objeto que encapsula la información de la cantidad actual en
 * bodega de un elemento.
 * 
 * Fecha de Creación: 12/08/2010
 * @author jvelezmu <a href="mailto:jvelezmu@intergrupo.com">e-mail</a>
 * @version 1.0
 */
public class WarehouseElementQuantityVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4962120489843023274L;
	private ElementTypeVO elementType;
	private MeasureUnitVO measureUnit;
	private ElementModelVO elementModel;
	private Double currentQuantity;
	
	public ElementTypeVO getElementType() {
		return elementType;
	}
	public void setElementType(ElementTypeVO elementType) {
		this.elementType = elementType;
	}
	public MeasureUnitVO getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(MeasureUnitVO measureUnit) {
		this.measureUnit = measureUnit;
	}
	public ElementModelVO getElementModel() {
		return elementModel;
	}
	public void setElementModel(ElementModelVO elementModel) {
		this.elementModel = elementModel;
	}
	public Double getCurrentQuantity() {
		return currentQuantity;
	}
	public void setCurrentQuantity(Double currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
}

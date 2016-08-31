/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.NotSerPartialRetirement;

/**
 * Objeto que encapsula la información de un NotSerPartialRetirement
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.NotSerPartialRetirement    
 */
public class NotSerPartialRetirementVO extends NotSerPartialRetirement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7858802697205147921L;
	
	private String warehouseNameSource;
	private String warehouseNameTarget;
	
	
	public String getWarehouseNameSource() {
		return warehouseNameSource;
	}
	
	public void setWarehouseNameSource(String warehouseNameSource) {
		this.warehouseNameSource = warehouseNameSource;
	}
	
	public String getWarehouseNameTarget() {
		return warehouseNameTarget;
	}
	
	public void setWarehouseNameTarget(String warehouseNameTarget) {
		this.warehouseNameTarget = warehouseNameTarget;
	}
}

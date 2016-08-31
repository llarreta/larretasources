/**
 * Creado 20/08/2010 9:34:51
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

import co.com.directv.sdii.audit.Auditable;

/**
 * Encapsula la información de cantidades totales de elementos por tipo y por bodega
 * dado el tipo de dato <code>whElWtyType</code> así:<br>
 * 'AQ' para cantidad actual de elemento en bodega
 * 'EN' para total de entradas de elementos a bodega
 * 'OU' para total salidas de elementos de bodega 
 * Fecha de Creación: 20/08/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class WarehouseElementQuantity implements Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4728917952624205314L;
	
	/**
	 * para cantidad actual de elemento en bodega
	 */
	public static final String WH_ELEMENT_QTY_TYPE_TOTAL_ACTUAL_QUANTITY = "AQ";
	
	/**
	 * para total de entradas de elementos a bodega
	 */
	public static final String WH_ELEMENT_QTY_TYPE_TOTAL_ENTRANCE_QUANTITY = "EN";
	
	/**
	 * para total salidas de elementos de bodega
	 */
	public static final String WH_ELEMENT_QTY_TYPE_TOTAL_OUTPUT_QUANTITY = "OU";
	
	

	/**
	 * Determina la cantidad de elementos
	 */
	private Double quantity;
	
	/**
	 * Deterima el tipo de elemento
	 */
	private Long elementTypeId;
	
	/**
	 * Define el identificador de la bodega
	 */
	private Long warehouseId;
	
	/**
	 * Define el significado de la cantidad. Ver las constantes de la clase.
	 */
	private String whElWtyType;

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getElementTypeId() {
		return elementTypeId;
	}

	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWhElWtyType() {
		return whElWtyType;
	}

	public void setWhElWtyType(String whElWtyType) {
		this.whElWtyType = whElWtyType;
	}

	@Override
	public String toString() {
		return "WarehouseElementQuantity [elementTypeId=" + elementTypeId
				+ ", quantity=" + quantity + ", warehouseId=" + warehouseId
				+ "]";
	}
}

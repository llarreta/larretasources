/**
 * Creado 30/08/2011 9:08:54
 */
package co.com.directv.sdii.model.dto;

/**
 * Clase encargada de encapsular la informacion para actualizar los seriales de elementos vinculados 
 * 
 * Fecha de Creación: 30/08/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class UpdateLinkedSerialsDTO implements java.io.Serializable{

	private static final long serialVersionUID = 3915531677456392753L;
	
	/*
	 * Datos del elemento que tiene vinculado el otro elemento
	 * */
	private Long warehouseId;
	private String serialCode;
	private String elementTypeCode;
	private Long countryId;
	
	/*
	 * Datos del elemento vinculado 
	 * */
	private String linkedSerialCode;
	
	/*
	 * Datos del elemento a ser vinculado
	 * */
	private String newLinkedSerialCode;

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}

	public String getLinkedSerialCode() {
		return linkedSerialCode;
	}

	public void setLinkedSerialCode(String linkedSerialCode) {
		this.linkedSerialCode = linkedSerialCode;
	}

	public String getNewLinkedSerialCode() {
		return newLinkedSerialCode;
	}

	public void setNewLinkedSerialCode(String newLinkedSerialCode) {
		this.newLinkedSerialCode = newLinkedSerialCode;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
}

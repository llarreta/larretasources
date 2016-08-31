package co.com.directv.sdii.model.dto;

import java.io.Serializable;


/**
*
* Link Serialized Filter Value Onject
* Objeto que encapsula los datos necesarios para el filtro cuando se van a vincular dos
* objetos serializados
*
* Fecha de Creación: Agosto 25, 2010
* @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
*
*/
public class LinkSerializedFilterVO implements Serializable {

	private static final long serialVersionUID = -7106639692004038748L;
	
	private Long warehouseId;
	private String elementOneCode;
	private String elementTwoCode;
	private String elementOneSerial;
	private String elementTwoSerial;
	
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public String getElementOneCode() {
		return elementOneCode;
	}
	public void setElementOneCode(String elementOneCode) {
		this.elementOneCode = elementOneCode;
	}
	public String getElementTwoCode() {
		return elementTwoCode;
	}
	public void setElementTwoCode(String elementTwoCode) {
		this.elementTwoCode = elementTwoCode;
	}
	public String getElementOneSerial() {
		return elementOneSerial;
	}
	public void setElementOneSerial(String elementOneSerial) {
		this.elementOneSerial = elementOneSerial;
	}
	public String getElementTwoSerial() {
		return elementTwoSerial;
	}
	public void setElementTwoSerial(String elementTwoSerial) {
		this.elementTwoSerial = elementTwoSerial;
	}
	
}

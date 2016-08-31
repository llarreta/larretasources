/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.directv.sdii.model.pojo.Serialized;

/**
 * Objeto que encapsula la información de un Serialized
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Serialized    
 */
public class SerializedVO extends Serialized implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -346449699500430951L;
	
	private SerializedVO replaceDeficientElement;
	private String deviceTypeId;//id del tipo de dispositivo en Field Services
	private boolean isWareHouse;


	private boolean isValidSerialLinked;
	
	/*
	 * Fecha de ingreso del elemento a la bodega CU 29
	 */
	private Date warehouseEntryDate;
	
	
	public void setReplaceDeficientElement(SerializedVO replaceDeficientElement) {
		this.replaceDeficientElement = replaceDeficientElement;
	}
	
	/**
	 * Para los elementos marcados como defectuosos se debe haber ingresado la información del elemento que lo reemplaza, 
	 * y el nuevo elemento debe existir en la bodega de disponibles de la cuadrilla
	 * @return SerializedVO
	 */
	public SerializedVO getReplaceDeficientElement() {
		return replaceDeficientElement;
	}
	
	/**
	 * Metodo: Representa si el elemento se encuentra en una bodega deseada
	 * @return true si se encuentra en la bodega deseada; false en otro caso
	 * @author gfandino
	 */
	public boolean isWareHouse() {
		return isWareHouse;
	}

	public void setWareHouse(boolean isWareHouse) {
		this.isWareHouse = isWareHouse;
	}

	/**
	 * Metodo: Representa si el serial del elemento corresponde al deseado
	 * @return true si el serial del elemento corresponde al deseado; false en otro caso
	 * @author gfandino
	 */
	public boolean isValidSerialLinked() {
		return isValidSerialLinked;
	}

	public void setValidSerialLinked(boolean isValidSerialLinked) {
		this.isValidSerialLinked = isValidSerialLinked;
	}

	public Date getWarehouseEntryDate() {
		return warehouseEntryDate;
	}

	public void setWarehouseEntryDate(Date warehouseEntryDate) {
		this.warehouseEntryDate = warehouseEntryDate;
	}
	
	public String getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	
	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		buff.append(getElementId());
		buff.append("|");
		buff.append(getElement().getLote() == null ? "" : getElement().getLote());
		/*buff.append("|");
		buff.append(getElement().getElementBrand() == null ? "Sin Marca" : getElement().getElementBrand().getBrandName());*/
		/*buff.append("|");
		buff.append(getElement().getElementClass() == null ? "Sin Clase" : getElement().getElementClass().getElementClassName());*/
		buff.append("|");
		buff.append(getElement().getElementType().getElementModel() == null ? "Sin Modelo" : getElement().getElementType().getElementModel().getModelName());
		buff.append("|");
		buff.append(getElement().getElementType() == null ? "Sin Tipo" : getElement().getElementType().getTypeElementName());
		buff.append("|");
		buff.append(getIrd() == null ? "" : getIrd());
		buff.append("|");
		buff.append(getSerialCode() == null ? "" : getSerialCode());
		buff.append("|");
		buff.append(getRegistrationDate() == null ? "" : df.format(getRegistrationDate()));
		return buff.toString();
	}
}

package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * 
 * DTO encargado de alamcenar los filtros para la consulta de 
 * elementos serialidos de un registro de importación
 * 
 * Fecha de Creación: 1/12/2011
 * @author waguilera <a href="mailto:waguilera@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class ImportLogElementsFilterDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5463646044679736410L;
	private Long importLogID;
	private boolean isSerialized;
	private String itemStatus;
	
	private String serialCode;
	
	public Long getImportLogID() {
		return importLogID;
	}
	public void setImportLogID(Long importLogID) {
		this.importLogID = importLogID;
	}
	public boolean isSerialized() {
		return isSerialized;
	}
	public void setSerialized(boolean isSerialized) {
		this.isSerialized = isSerialized;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}

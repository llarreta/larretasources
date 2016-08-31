package co.com.directv.sdii.model.dto;

/**
 * Representa los parametros del filtro realizar la consulta de los items de registros de importacion
 * @author hcorredor
 *
 */
public class ModifyImportLogItemDTO implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5145936631869281958L;

	/**
	 * Codigo del status del registro de importacion
	 */
	private String importLogStatusId;
	
	/**
	 * serialNumber, número Serial
	 */
	private String serialNumber;
	
	/**
	 * importLogId, id del Registro de importación.
	 */
	private Long importLogId;
	
	/**
	 * isSerialized, Indica si se debe filtrar por si el elemento es serializado o no.
	 */
	private String isSerialized; 
	

	

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setImportLogId(Long importLogId) {
		this.importLogId = importLogId;
	}

	public Long getImportLogId() {
		return importLogId;
	}

	public void setImportLogStatusId(String importLogStatusId) {
		this.importLogStatusId = importLogStatusId;
	}

	public String getImportLogStatusId() {
		return importLogStatusId;
	}

	public void setIsSerialized(String isSerialized) {
		this.isSerialized = isSerialized;
	}

	public String getIsSerialized() {
		return isSerialized;
	}

	
	
	
	

}

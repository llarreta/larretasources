package co.com.directv.sdii.model.dto;

import java.util.Date;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.ImportLogStatus;

/**
 * Representa los parametros del filtro realizar la consulta de los registros de importacion
 * @author Juan Carlos Nova - jnova@intergrupo.com
 *
 */
public class ModifyImportLogDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7129767352045671302L;
	
	/**
	 * ID del registro de importacion
	 */
	private Long importLogId;
	/**
	 * Codigo del status del registro de importacion
	 */
	private String importLogStatusCode;
	
	/**
	 * Fecha de creacion del registro de importacion
	 */
	private Date creationDate;
	/**
	 * Fecha de creacion del registro de importacion inicial
	 */
	private Date creationDateINI;
	/**
	 * Fecha de creacion del registro de importacion final
	 */
	private Date creationDateEND;
	/**
	 * Fecha de entrega estimada 
	 */
	private Date deliveryDate;
	/**
	 * Date Fecha de embarque 
	 */
	private Date shippingDate;
	/**
	 * dealerId, Operador Logistico
	 */
	private Long dealerId;
	/**
	 * elementTypeId, Tipo de Elemento
	 */
	private Long elementTypeId;
	/**
	 * serialNumber, número Serial
	 */
	private String serialNumber;
	
	/**
	 * purchaseOrder, orden de compra
	 */
	private String purchaseOrder;
	
	/**
	 * importDoc, Documento de Importación
	 */
	private String importDoc;
	
	/**
	 * legalDoc, Documento legal
	 */
	private String legalDoc;
	
	/**
	 * supplierId, Identificador del Proveedor 
	 */
	private Long supplierId;
	
	/**
	 * serialized, si la consulta es de un objeto serializado
	 */
	private Boolean serialized;
	
	public Long getImportLogId() {
		return importLogId;
	}
	public void setImportLogId(Long importLogId) {
		this.importLogId = importLogId;
	}
	public String getImportLogStatusCode() {
		return importLogStatusCode;
	}
	public void setImportLogStatusCode(String importLogStatus) {
		this.importLogStatusCode = importLogStatus;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public Long getDealerId() {
		return dealerId;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	public String getPurchaseOrder() {
		return purchaseOrder;
	}
	public void setImportDoc(String importDoc) {
		this.importDoc = importDoc;
	}
	public String getImportDoc() {
		return importDoc;
	}
	public void setSerialized(Boolean serialized) {
		this.serialized = serialized;
	}
	public Boolean getSerialized() {
		return serialized;
	}
	public void setElementTypeId(Long elementTypeId) {
		this.elementTypeId = elementTypeId;
	}
	public Long getElementTypeId() {
		return elementTypeId;
	}
	public void setCreationDateINI(Date creationDateINI) {
		this.creationDateINI = creationDateINI;
	}
	public Date getCreationDateINI() {
		return creationDateINI;
	}
	public void setCreationDateEND(Date creationDateEND) {
		this.creationDateEND = creationDateEND;
	}
	public Date getCreationDateEND() {
		return creationDateEND;
	}
	public void setLegalDoc(String legalDoc) {
		this.legalDoc = legalDoc;
	}
	public String getLegalDoc() {
		return legalDoc;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getSupplierId() {
		return supplierId;
	}

	/**
	 * Metodo que valida que en el DTO enviado de filtro por lo menos un parametro no venga nulo
	 * @throws BusinessException En caso que todos los parametros sean nulos
	 */
	public void validateCiteria() throws BusinessException {
		boolean isAllNull = true;
		if( this.importLogId != null )
			isAllNull = false;
		else if( this.importLogStatusCode != null)
			isAllNull = false;
		else if( this.creationDate  != null)
			isAllNull = false;
		else if( this.deliveryDate != null)
			isAllNull = false;
		else if( this.shippingDate != null)
			isAllNull = false;		
		else if( this.dealerId  != null)
			isAllNull = false;
		else if( this.getElementTypeId() != null)
			isAllNull = false;
		else if( this.serialNumber != null)
			isAllNull = false;
		else if( this.purchaseOrder != null)
			isAllNull = false;
		else if( this.importDoc != null)
			isAllNull = false;
		else if( this.supplierId != null)
			isAllNull = false;
		if(isAllNull){
			throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() ); 
		}
	}
	

}

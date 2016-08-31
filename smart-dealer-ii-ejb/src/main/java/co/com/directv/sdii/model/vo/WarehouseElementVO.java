/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import co.com.directv.sdii.model.pojo.WarehouseElement;

/**
 * Objeto que encapsula la información de un WarehouseElement
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WarehouseElement    
 */
public class WarehouseElementVO extends WarehouseElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6816509947637822019L;

	/**
	 * Indica si es modificado
	 */
	private boolean modified;
	
	/**
	 * Cantidad de ajuste
	 */
	private Double ajustmentQauntity;
	
	private Long workOrderId;
	
	private String workOrderCode;
	
	private String importLogPurchaseOrder;
	
	private Long importLogId;
	
	private Long referenceId;
	
	private String importDoc;
	
	/**
	 * Variable utilizada para CU ADS 64
	 */
	private Double quantityToReturn;
	
	private String warehouseName;
	
	private String companyName;
	
	private String branchName;
	
	public Double getQuantityToReturn() {
		return quantityToReturn;
	}

	public void setQuantityToReturn(Double quantityToReturn) {
		this.quantityToReturn = quantityToReturn;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public Double getAjustmentQauntity() {
		return ajustmentQauntity;
	}

	public void setAjustmentQauntity(Double ajustmentQauntity) {
		this.ajustmentQauntity = ajustmentQauntity;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getWorkOrderCode() {
		return workOrderCode;
	}

	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
	}

	public String getImportLogPurchaseOrder() {
		return importLogPurchaseOrder;
	}

	public void setImportLogPurchaseOrder(String importLogPurchaseOrder) {
		this.importLogPurchaseOrder = importLogPurchaseOrder;
	}

	public Long getImportLogId() {
		return importLogId;
	}

	public void setImportLogId(Long importLogId) {
		this.importLogId = importLogId;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	
	public void setImportDoc(String importDoc) {
		this.importDoc = importDoc;
	}

	public String getImportDoc() {
		return importDoc;
	}

	public String toXLSString(){
		StringBuffer buff = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		buff.append(super.getId());
		buff.append("|");
		if(super.getSerialized() != null){
			buff.append(super.getSerialized().getIrd() == null ? "" : super.getSerialized().getIrd());
			buff.append("|");
			buff.append(super.getSerialized().getSerialCode() == null ? "" : super.getSerialized().getSerialCode());
			buff.append("|");
			buff.append(super.getSerialized().getElement().getElementType().getElementModel() == null ? "" : getSerialized().getElement().getElementType().getElementModel().getModelName());
			buff.append("|");
			buff.append(super.getSerialized().getElement().getElementType() == null ? "" : getSerialized().getElement().getElementType().getTypeElementName());
			buff.append("|");
			buff.append(super.getSerialized().getRegistrationDate() == null ? "" : df.format(super.getSerialized().getRegistrationDate()));
			buff.append("|");
		} else if (super.getNotSerialized() != null){
			buff.append("Sin IRD");
			buff.append("|");
			buff.append("Sin Serial");
			buff.append("|");
			buff.append("|");
			buff.append(super.getNotSerialized().getElement().getElementType().getElementModel() == null ? "" : getNotSerialized().getElement().getElementType().getElementModel().getModelName());
			buff.append("|");
			buff.append(super.getNotSerialized().getElement().getElementType() == null ? "" : getNotSerialized().getElement().getElementType().getTypeElementName());
			buff.append("|");
			buff.append(super.getNotSerialized().getRegistrationDate() == null ? "" : df.format(super.getNotSerialized().getRegistrationDate()));
			buff.append("|");
		}
		
		buff.append(super.getInitialQuantity() == null ? "" : super.getInitialQuantity());
		buff.append("|");
		buff.append(super.getActualQuantity() == null ? "" : super.getActualQuantity());
		buff.append("|");
		buff.append(super.getMovementDate() == null ? "" : df.format(super.getMovementDate()));
		buff.append("|");
		buff.append(super.getMovementType() == null ? "" : super.getMovementType().getMovTypeName());
		buff.append("|");
		buff.append(super.getRecordStatus() == null ? "" : super.getRecordStatus().getRecordStatusName());
		return buff.toString();
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}

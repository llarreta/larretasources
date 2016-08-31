package co.com.directv.sdii.reports.dto;

import java.util.Date;

public class WarehouseElementDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319436798176021359L;

	private boolean serialized;

	private Long id;
	private String unitName;
	private Double actualQuantity;
	private String lote;
	private String elementStatusName;
	private Date entryDate;
	private String importLogPurchaseOrder;
	private Long importLogId;
	private Long referenceId;
	private String typeElementName;

	private String branchName;
	private String dealerName;
	private String whCode;
	private String modelName;
	private String serialCode;
	private String rid;
	private String linkedSerial;

	private Date registrationDate;


	private String importLogIdAndReferenceId;

	private String cityName;
	private String address;
	private String ibsCode;
	private String typeElementCode;
	private Long typeElementId;
	private Date exitDate;
	private String movementCode;
	private String woCode;

	private Double adjustmentQuantity;

	private String measureUnit;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getElementStatusName() {
		return elementStatusName;
	}
	public void setElementStatusName(String elementStatusName) {
		this.elementStatusName = elementStatusName;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
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
	public String getTypeElementName() {
		return typeElementName;
	}
	public void setTypeElementName(String typeElementName) {
		this.typeElementName = typeElementName;
	}
	public String getImportLogIdAndReferenceId() {
		return importLogIdAndReferenceId;
	}
	public void setImportLogIdAndReferenceId(String importLogIdAndReferenceId) {
		this.importLogIdAndReferenceId = importLogIdAndReferenceId;
	}
	public Double getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(Double actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public String getLinkedSerial() {
		return linkedSerial;
	}
	public void setLinkedSerial(String linkedSerial) {
		this.linkedSerial = linkedSerial;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIbsCode() {
		return ibsCode;
	}
	public void setIbsCode(String ibsCode) {
		this.ibsCode = ibsCode;
	}
	public String getTypeElementCode() {
		return typeElementCode;
	}
	public void setTypeElementCode(String typeElementCode) {
		this.typeElementCode = typeElementCode;
	}
	public Date getExitDate() {
		return exitDate;
	}
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}
	public String getMovementCode() {
		return movementCode;
	}
	public void setMovementCode(String movementCode) {
		this.movementCode = movementCode;
	}
	public String getWoCode() {
		return woCode;
	}
	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}
	public Long getTypeElementId() {
		return typeElementId;
	}
	public void setTypeElementId(Long typeElementId) {
		this.typeElementId = typeElementId;
	}
	public Double getAdjustmentQuantity() {
		return adjustmentQuantity;
	}
	public void setAdjustmentQuantity(Double adjustmentQuantity) {
		this.adjustmentQuantity = adjustmentQuantity;
	}
	public boolean getSerialized() {
		return serialized;
	}
	public void setSerialized(boolean serialized) {
		this.serialized = serialized;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
